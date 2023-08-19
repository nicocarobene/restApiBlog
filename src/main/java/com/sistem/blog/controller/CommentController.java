package com.sistem.blog.controller;

import com.sistem.blog.DTO.CommentDTO;
import com.sistem.blog.services.CommentServiceIMP;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentServiceIMP commentService;
    @GetMapping("/post/{id}")
    public List<CommentDTO> getAllComment(
            @PathVariable(value="id")long id
    ){
      return commentService.getAllComment(id);
    }
    @GetMapping("/post/{idPost}/{id}")
    public ResponseEntity<CommentDTO> getCommentById(
            @PathVariable(value = "id") Long comment_id,
            @PathVariable(value = "idPost") Long post_id
            ){
        CommentDTO comment= commentService.getCommentById(comment_id,post_id);
        return ResponseEntity.status(HttpStatus.FOUND).body(comment);
    }

    @PutMapping("/post/{idPost}/{id}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(value = "id") Long comment_id,
            @PathVariable(value = "idPost") Long post_id,
            @Valid @RequestBody CommentDTO newComment
    ){
        CommentDTO comment= commentService.updateComment(comment_id,post_id,newComment);
        return ResponseEntity.ok(comment);
    }
    @PostMapping("/{id}")
    public ResponseEntity<CommentDTO> createComment(
            @PathVariable(value = "id") Long id,
            @Valid @RequestBody CommentDTO commentReq
    ){
        CommentDTO newComment= commentService.createComment(id, commentReq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newComment);
    }

    @DeleteMapping("/post/{idPost}/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "id") Long comment_id,
            @PathVariable(value = "idPost") Long post_id
    ){
        commentService.deleteComment(comment_id, post_id);
        return ResponseEntity.ok("Comment with ID " + comment_id + " has been deleted.");
    }
}
