package com.example.blog.controllers;

import com.example.blog.exceptions.NotFoundException;
import com.example.blog.models.Post;
import com.example.blog.repositories.PostReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class PostAdminController {

    @Autowired
    private PostReposity postRepository;

    @PutMapping("/update")
    void put(@RequestBody Post post) {
        if (!postRepository.existsById(post.getId())) {
            throw new NotFoundException(String.format("Post id %d is not found", post.getId()));
        }
    }

    @GetMapping
    Iterable<Post> get() {
        return postRepository.findAll(Sort.by("id"));
    }

//    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/post/{id}")
    void delete(@PathVariable int id) {

        if (!postRepository.existsById(id)) {
            throw new NotFoundException(String.format("Post id %d is not found", id));
        }
        postRepository.deleteById(id);
    }
}
