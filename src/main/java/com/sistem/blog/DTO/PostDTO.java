package com.sistem.blog.DTO;

import com.sistem.blog.model.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public class PostDTO {
    private Long id;
    @NotEmpty
    @Size(min=2, message = "Post title must have more than 2 characters")
    private String title;
    @NotEmpty
    @Size(min=5, message = "Post content must have more than 5 characters")
    private String content;
    @NotEmpty
    @Size(min=5, message = "Post description must have more than 5 characters")
    private String description;
    private Set<Comment> listOfComment;

    public Set<Comment> getListOfComment() {
        return listOfComment;
    }

    public void setListOfComment(Set<Comment> listOfComment) {
        this.listOfComment = listOfComment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PostDTO() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
