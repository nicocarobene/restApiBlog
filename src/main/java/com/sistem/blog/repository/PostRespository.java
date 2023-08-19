package com.sistem.blog.repository;

import com.sistem.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRespository extends JpaRepository<Post, Long> {
}
