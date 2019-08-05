package com.example.book.controllers;

import com.example.book.reposities.BookReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    BookReposity bookReposity;

    @RequestMapping("/index")
    public String index(Model model){

        model.addAttribute("books",bookReposity.findAll());

        return "Home";
    }
}
