package com.itschool.springbootdeveloper.controller;

import com.itschool.springbootdeveloper.domain.Post;
import com.itschool.springbootdeveloper.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostApiController {
    @Autowired
    PostService postService;

    @GetMapping("")
    public List<Post> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return posts;
    }

    @GetMapping("{post_id}")
    public Post findPostById(@PathVariable Integer post_id) {
        return postService.findPostById(post_id);
    }

    @GetMapping("search-by-content/{content}")
    public List<Post> findPostsByContent(@PathVariable String content) {
        return postService.findPostsByContent(content);
    }
}
