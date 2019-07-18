package com.example.blog.controllers;

import com.example.blog.exceptions.NotFoundException;
import com.example.blog.models.Post;
import com.example.blog.models.Tag;
import com.example.blog.repositories.PostReposity;
import com.example.blog.repositories.TagReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.*;

@Controller
@RequestMapping("/user")
public class PostUserController {

    @Autowired
    private PostReposity postRepository;

    @Autowired
    private TagReposity tagReposity;

    @RequestMapping(value = "/post/{id}",method = RequestMethod.GET)
    public Post get(@PathVariable int id) {
        Optional<Post> optionalPost = postRepository.findById(id);

        if (optionalPost.isPresent()) {
            return optionalPost.get();
        }
        throw new NotFoundException(String.format("Post id %d is not found", id));
    }

    @PostMapping("/post")
    String post(Post post,String tag) {
        post.setId(0);
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        post.setDate(date);
        post.setTags(addTag(tag));

        postRepository.save(post);

        return "redirect:/home";
    }

    public Set<Tag> addTag(String tag) {
        Set<Tag> tags = new HashSet<>();
        String[] tagsName = tag.split(",");

        for (String tagName : tagsName) {
            if (!tagReposity.findByName(tagName).isPresent()) {
                Tag tag1 = new Tag();
                tag1.setName(tagName);
                tagReposity.save(tag1);
            }
            tags.add(tagReposity.findByName(tagName).get());
        }

        return tags;
    }

    @PutMapping("/update")
    void put(@RequestBody Post post) {
        if (!postRepository.existsById(post.getId())) {
            throw new NotFoundException(String.format("Post id %d is not found", post.getId()));
        }
    }

    @DeleteMapping(value = "/post/{id}")
    void delete(@PathVariable int id) {

        if (!postRepository.existsById(id)) {
            throw new NotFoundException(String.format("Post id %d is not found", id));
        }
        postRepository.deleteById(id);
    }
}
