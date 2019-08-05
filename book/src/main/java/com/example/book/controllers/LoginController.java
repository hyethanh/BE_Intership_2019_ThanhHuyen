package com.example.book.controllers;

import com.example.book.dao.Login;
import com.example.book.models.AuthToken;
import com.example.book.reposities.BookReposity;
import com.example.book.reposities.UserReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    AuthController authController;

    @Autowired
    UserReposity userReposity;

    @Autowired
    private BookReposity bookReposity;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String indexPage() {
        return "Login";
    }

    @RequestMapping(value = "/authen",method = RequestMethod.POST)
    public String authen(Login login, HttpSession session, Model model){

        ResponseEntity<AuthToken> token = (ResponseEntity<AuthToken>)authController.login(login);

        session.setAttribute("email",login.getEmail());
        session.setAttribute("password",login.getPassword());
        session.setAttribute("books",bookReposity.findAll());

        return "redirect:/index";
    }

}
