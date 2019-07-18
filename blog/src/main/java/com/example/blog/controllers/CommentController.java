package com.example.blog.controllers;

import com.example.blog.models.Comment;
import com.example.blog.models.Post;
import com.example.blog.repositories.CommentRepository;
import com.example.blog.repositories.PostReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostReposity postReposity;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView post(@RequestParam int id) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("comments",postReposity.findById(id).get().getComments());
        return mav;
    }

    @PostMapping("/post/{id}")
    String comment(@PathVariable int id, Comment comment) {
        Post post = postReposity.getOne(id);

        if(post == null){
            //404
        }

        comment.setId(0);
        Date date = new Date(System.currentTimeMillis());
        comment.setDate(date);

        comment = commentRepository.save(comment);
        post.getComments().add(comment);
        postReposity.save(post);

        return "redirect:/post/?id=" + id;
    }
}
