package com.sistem.blog.DTO;

import com.sistem.blog.model.Post;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.math.BigInteger;


public class CommentDTO {
    private Long id;
    @NotEmpty
    @Size(min=2, message = "Comment name must have more than 2 characters")
    private String name;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min=2, message = "Comment must have more than 2 characters")
    private String comment;

    private long post;

    public CommentDTO(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getPost() {
        return post;
    }

    public void setPost(long post) {
        this.post = post;
    }
}
