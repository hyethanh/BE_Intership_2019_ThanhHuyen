package com.example.blog.controllers;

import com.example.blog.repositories.PostReposity;
import com.example.blog.repositories.TagReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostReposity postRepository;

    @Autowired
    private TagReposity tagReposity;

    @GetMapping("")
    public String postBlog(){

        return "post-blog";
    }

    @RequestMapping(value = "post", method = RequestMethod.GET)
    public ModelAndView post() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("posts", postRepository.findAll());
        return mav;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView postid(@RequestParam int id){
        ModelAndView mav = new ModelAndView();
        mav.addObject("post",postRepository.findById(id).get());
        mav.addObject("comments",postRepository.findById(id).get().getComments());
        mav.addObject("tags",postRepository.findById(id).get().getTags());
        return mav;
    }

    @RequestMapping("/delete")
    public String delete(Model model){

        model.addAttribute("posts", postRepository.findAll());

        return "delete";
    }
}
