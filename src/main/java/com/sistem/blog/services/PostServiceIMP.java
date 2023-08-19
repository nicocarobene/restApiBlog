package com.sistem.blog.services;

import com.sistem.blog.DTO.ListPostDTO;
import com.sistem.blog.DTO.PostDTO;
import com.sistem.blog.exceptions.ResourceAlreadyExistsException;
import com.sistem.blog.exceptions.ResourceNotFoundException;
import com.sistem.blog.model.Comment;
import com.sistem.blog.model.Post;
import com.sistem.blog.repository.CommentRepository;
import com.sistem.blog.repository.PostRespository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostServiceIMP implements PostService{

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRespository PostEM;
    @Autowired
    private CommentRepository CommentEm;
    @Override
    public PostDTO createPost(PostDTO postReq) {
        //convert DTO to Entity
        Post post= convertDtoToPost(postReq);
        //exception handling and save post
        Post newPost;
        try{
            newPost=PostEM.save(post);
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistsException("Title");
        }

        //convert Entity to DTO
        PostDTO postResp= convertPostToDto(newPost);
        return postResp;
    }

    @Override
    public ListPostDTO getAllPost(int page, int limit, String sortBy, String sortDir) {
        Sort SortDir= sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageReq= PageRequest.of(page,limit, SortDir);
        Page<Post> posts= PostEM.findAll(pageReq);
        List<PostDTO> listpost=  posts.stream().map(this::convertPostToDto).toList();

        ListPostDTO respPost= new ListPostDTO();
        respPost.setAllResult((int) posts.getTotalElements());
        respPost.setPage(posts.getNumber());
        respPost.setData(listpost);
        respPost.setLimit(posts.getSize());
        respPost.setLastPage(posts.isLast());
        return respPost;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = PostEM.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        Set<Comment> comments = CommentEm.findByPostId(id);
        PostDTO newPost=convertPostToDto(post);
        newPost.setListOfComment(comments);
        return newPost;
    }

    @Override
    public PostDTO updatePost(PostDTO reqPost, Long id) {
        Post post = PostEM.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(reqPost.getTitle());
        post.setContent(reqPost.getContent());
        post.setDescription(reqPost.getDescription());
        try{
            Post newPost=PostEM.save(post);
            return convertPostToDto(newPost);
        }catch (DataIntegrityViolationException e){
            throw new ResourceAlreadyExistsException("Title", "id", id);
        }
    }

    @Override
    public void deletePost(Long id) {
        Post post;
        try{
            post= PostEM.getReferenceById(id);
        }catch (EntityNotFoundException  e) {
            throw new ResourceNotFoundException("Post", "id", id);
        }
        PostEM.delete(post);
    }

    private PostDTO convertPostToDto (Post post){
        PostDTO postDTO = modelMapper.map(post, PostDTO.class);
        Set<Comment> comments = CommentEm.findByPostId(post.getId());
        postDTO.setListOfComment(new HashSet<>(comments));
        return postDTO;
        /*
        PostDTO DTOpost= new PostDTO();
        DTOpost.setTitle(post.getTitle());
        DTOpost.setId(post.getId());
        DTOpost.setDescription(post.getDescription());
        DTOpost.setContent(post.getContent());
        return DTOpost;
         */
    }
    private Post convertDtoToPost (PostDTO postDTO){
        return modelMapper.map(postDTO, Post.class);
        /*
        Post post= new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setDescription(postDTO.getDescription());
        return post;
        */
    }

}
