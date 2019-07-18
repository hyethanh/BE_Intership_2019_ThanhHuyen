package com.example.blog.controllers;

import com.example.blog.exceptions.NotFoundException;
import com.example.blog.models.Post;
import com.example.blog.repositories.PostReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class PostLoginController {

    @Autowired
    private PostReposity postRepository;

    @PostMapping("/post")
    void post(@RequestBody @Validated Post post) {
        post.setId(0);
        postRepository.save(post);
    }

    @PutMapping("/update")
    void put(@RequestBody Post post) {
        if (!postRepository.existsById(post.getId())) {
            throw new NotFoundException(String.format("Post id %d is not found", post.getId()));
        }
    }
}
