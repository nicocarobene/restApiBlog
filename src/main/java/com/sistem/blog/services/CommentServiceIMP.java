package com.sistem.blog.services;

import com.sistem.blog.DTO.CommentDTO;
import com.sistem.blog.exceptions.BlogAppException;
import com.sistem.blog.exceptions.ResourceNotFoundException;
import com.sistem.blog.model.Comment;
import com.sistem.blog.model.Post;
import com.sistem.blog.repository.CommentRepository;
import com.sistem.blog.repository.PostRespository;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CommentServiceIMP implements CommentService{
    @Autowired
    private PostRespository postEM;
    @Autowired
    private CommentRepository CommentEM;

    private ModelMapper modelMapper;

    public CommentServiceIMP(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;

        // Configuración personalizada para la conversión Comment -> CommentDTO
        modelMapper.addMappings(new PropertyMap<Comment, CommentDTO>() {
            @Override
            protected void configure() {
                map().setPost(source.getPost().getId());
            }
        });
    }

    @Override
    public List<CommentDTO> getAllComment(long post_id) {
        Post post = postEM.findById(post_id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
        Set<Comment> comments= CommentEM.findByPostId(post_id);
        return comments.stream().map(comment->convertCommentToDto(comment,post.getId())).toList();
    }

    @Override
    public CommentDTO getCommentById(long comment_id, long post_id) {
        Comment comment= CommentEM.findById(comment_id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", comment_id));
        Post post = postEM.findById(post_id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));

        if(comment.getPost().getId().equals(post.getId())){
            return convertCommentToDto(comment, post_id);
        }
        throw new BlogAppException(HttpStatus.BAD_REQUEST, "the comment does not exist in this post");
    }

    @Override
    public CommentDTO updateComment(long comment_id, long post_id, CommentDTO newComment) {
        Comment comment= CommentEM.findById(comment_id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", comment_id));
        Post post = postEM.findById(post_id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));

        if(comment.getPost().getId().equals(post.getId())){
            comment.setName(newComment.getName());
            comment.setEmail(newComment.getEmail());
            comment.setComment(newComment.getComment());
            Comment commentNew= CommentEM.save(comment);
            return convertCommentToDto(commentNew, post_id);
        }
        throw new BlogAppException(HttpStatus.BAD_REQUEST,"the comment does not exist in this post" );
    }

    @Override
    public void deleteComment(long comment_id, long post_id) {
        Comment comment= CommentEM.findById(comment_id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", comment_id));
        Post post = postEM.findById(post_id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
        if(comment.getPost().getId().equals(post.getId())) CommentEM.delete(comment);
        else{
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"the comment does not exist in this post");
        }
    }

    @Override
    public CommentDTO createComment(long post_id, CommentDTO commentDTO) {
        Post post = postEM.findById(post_id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", post_id));
        Comment comment= convertDtoToComment(commentDTO);
        comment.setPost(post);
        Comment newComment= CommentEM.save(comment);
        return convertCommentToDto(newComment,post_id);
    }

    private CommentDTO convertCommentToDto (Comment comment, long post_id){
        CommentDTO newComment= modelMapper.map(comment,CommentDTO.class);
        newComment.setPost(post_id);
        return newComment;
        /*
        CommentDTO DTOcomment= new CommentDTO();
        DTOcomment.setComment(comment.getComment());
        DTOcomment.setEmail(comment.getEmail());
        DTOcomment.setName(comment.getName());
        DTOcomment.setId(comment.getId());
        DTOcomment.setPost(post_id);
        return DTOcomment;
        */
    }
    private Comment convertDtoToComment (CommentDTO commentDTO){
        return modelMapper.map(commentDTO,Comment.class);
        /*
        Comment comment= new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());
        comment.setId(commentDTO.getId());
        return comment;
         */
    }

}
