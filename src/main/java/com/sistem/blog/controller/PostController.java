package com.sistem.blog.controller;

import com.sistem.blog.DTO.ListPostDTO;
import com.sistem.blog.DTO.PostDTO;
import com.sistem.blog.services.PostServiceIMP;
import com.sistem.blog.utils.AppCONSTANTS;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostServiceIMP postService;

    @GetMapping
    public ListPostDTO getAllPost(
            @RequestParam(value = "page", defaultValue = AppCONSTANTS.NUM_PAGE_DEFAULT, required = false) int numPage,
            @RequestParam(value = "limit", defaultValue = AppCONSTANTS.LIMIT_RESULT, required = false) int limit,
            @RequestParam(value = "sortBy", defaultValue = AppCONSTANTS.SORTBY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppCONSTANTS.SORTDIR, required = false) String sortDir
    ){
        return postService.getAllPost(numPage,limit, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long id){
        PostDTO post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postReq){
        PostDTO newPost= postService.createPost(postReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO postReq){
        PostDTO post = postService.updatePost(postReq, id);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
        postService.deletePost(id);
        return ResponseEntity.ok("Post with ID " + id + " has been deleted.");
    }
}
