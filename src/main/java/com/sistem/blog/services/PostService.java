package com.sistem.blog.services;

import com.sistem.blog.DTO.ListPostDTO;
import com.sistem.blog.DTO.PostDTO;

import java.util.List;

public interface PostService {
    public PostDTO createPost(PostDTO postDTO);
    public ListPostDTO getAllPost(int page, int limit, String sortBy, String sortDir);
    public PostDTO getPostById(long id);
    public PostDTO updatePost(PostDTO reqPost, Long id);
    public void deletePost(Long id);
}
