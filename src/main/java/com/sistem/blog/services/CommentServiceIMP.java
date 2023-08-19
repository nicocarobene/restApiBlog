package com.sistem.blog.services;

import com.sistem.blog.DTO.CommentDTO;
import com.sistem.blog.exceptions.BlogAppException;
import com.sistem.blog.exceptions.ResourceNotFoundException;
import com.sistem.blog.model.Comment;
import com.sistem.blog.model.Post;
import com.sistem.blog.repository.CommentRepository;
import com.sistem.blog.repository.PostRespository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class CommentServiceIMP implements CommentService{
    @Autowired
    PostRespository postEM;
    @Autowired
    CommentRepository CommentEM;

    @Override
    public List<CommentDTO> getAllComment(long post_id) {
        Post post;
        try{
            post= postEM.getReferenceById(post_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Post", "id", post_id);
        }
        List<Comment> comments= CommentEM.findByPostId(post_id);
        return comments.stream().map(comment->convertCommentToDto(comment,post.getId())).toList();
    }

    @Override
    public CommentDTO getCommentById(long comment_id, long post_id) {
        Post post;
        Comment comment;
        try{
            post= postEM.getReferenceById(post_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Post", "id", post_id);
        }
        try{
            comment= CommentEM.getReferenceById(comment_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Comment", "id", comment_id);
        }
        if(comment.getPost().getId().equals(post.getId())){
            return convertCommentToDto(comment, post_id);
        }
        throw new BlogAppException(HttpStatus.BAD_REQUEST, "the comment does not exist in this post");
    }

    @Override
    public CommentDTO updateComment(long comment_id, long post_id, CommentDTO newComment) {
        Post post;
        Comment comment;
        try{
            post= postEM.getReferenceById(post_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Post", "id", post_id);
        }
        try{
            comment= CommentEM.getReferenceById(comment_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Comment", "id", comment_id);
        }
        if(comment.getPost().getId().equals(post.getId())){
            comment.setName(newComment.getName());
            comment.setEmail(newComment.getEmail());
            comment.setComment(newComment.getComment());
            Comment commentNew= CommentEM.save(comment);
            CommentDTO comment1= convertCommentToDto(commentNew, post_id);
            return comment1;
        }
        throw new BlogAppException(HttpStatus.BAD_REQUEST,"the comment does not exist in this post" );
    }

    @Override
    public void deleteComment(long comment_id, long post_id) {
        Post post;
        Comment comment;
        try{
            post= postEM.getReferenceById(post_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Post", "id", post_id);
        }
        try{
            comment= CommentEM.getReferenceById(comment_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Comment", "id", comment_id);
        }
        if(comment.getPost().getId().equals(post.getId())) CommentEM.delete(comment);
        throw new BlogAppException(HttpStatus.BAD_REQUEST,"the comment does not exist in this post");
    }

    @Override
    public CommentDTO createComment(long post_id, CommentDTO commentDTO) {
        Post post;
        try{
            post= postEM.getReferenceById(post_id);
        }catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Post", "id", post_id);
        }
        Comment comment= convertDtoToComment(commentDTO);
        comment.setPost(post);
        Comment newComment= CommentEM.save(comment);
        return convertCommentToDto(newComment,post_id);
    }

    private CommentDTO convertCommentToDto (Comment comment, long post_id){
        CommentDTO DTOcomment= new CommentDTO();
        DTOcomment.setComment(comment.getComment());
        DTOcomment.setEmail(comment.getEmail());
        DTOcomment.setName(comment.getName());
        DTOcomment.setId(comment.getId());
        DTOcomment.setPost(post_id);
        return DTOcomment;
    }
    private Comment convertDtoToComment (CommentDTO commentDTO){
        Comment comment= new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        comment.setId(commentDTO.getId());
        return comment;
    }

}
