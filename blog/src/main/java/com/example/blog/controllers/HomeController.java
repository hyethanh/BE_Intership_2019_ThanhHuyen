package com.example.blog.controllers;

import com.example.blog.repositories.PostReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    PostReposity postReposity;


    @RequestMapping("/home")
    public String home(Model model){

        model.addAttribute("posts", postReposity.findAll());

        return "home";
    }
}
