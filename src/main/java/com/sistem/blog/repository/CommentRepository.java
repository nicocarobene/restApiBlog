package com.sistem.blog.repository;

import com.sistem.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    public List<Comment> findByPostId(long post_id);

}
