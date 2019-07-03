package com.example.blog.controllers;

import com.example.blog.exceptions.NotFoundException;
import com.example.blog.models.Post;
import com.example.blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/{id}")
    public Post get(@PathVariable int id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            return optionalPost.get();
        }
        throw new NotFoundException(String.format("Post id %d is not found", id));
    }

    @GetMapping
    Iterable<Post> get() {
        return postRepository.findAll(Sort.by("id"));
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable int id) {

        if (!postRepository.existsById(id)) {
            throw new NotFoundException(String.format("Post id %d is not found", id));
        }
        postRepository.deleteById(id);
    }

    @PostMapping()
    void post(@RequestBody @Validated Post post) {
        post.setId(0);
        postRepository.save(post);
    }

    @PutMapping
    void put(@RequestBody Post post) {
        if (!postRepository.existsById(post.getId())) {
            throw new NotFoundException(String.format("Post id %d is not found", post.getId()));
        }
    }
}
