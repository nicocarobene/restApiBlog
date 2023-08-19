package com.sistem.blog.services;

import com.sistem.blog.DTO.CommentDTO;

import java.util.List;

public interface CommentService {
    public CommentDTO createComment ( long post_id, CommentDTO commentDTO );
    public List<CommentDTO> getAllComment(long post_id);
    public CommentDTO getCommentById(long comment_id, long post_id);
    public CommentDTO updateComment(long comment_id, long post_id, CommentDTO newComment);
    public void deleteComment(long comment_id, long post_id);
}
/*

    public ListPostDTO getAllPost(int page, int limit, String sortBy, String sortDir);
    public PostDTO getPostById(long id);
    public PostDTO updatePost(PostDTO reqPost, Long id);
    public void deletePost(Long id);
 */