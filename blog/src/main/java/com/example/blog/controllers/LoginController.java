package com.example.blog.controllers;

import com.example.blog.dao.Login;
import com.example.blog.models.AuthToken;
import com.example.blog.models.User;
import com.example.blog.repositories.PostReposity;
import com.example.blog.repositories.UserReposity;
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
    private PostReposity postRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String indexPage() {
        return "loginPage";
    }

    @RequestMapping(value = "/authen", method = RequestMethod.POST)
    public String authen(Login login, HttpSession session, Model model){
        ResponseEntity<AuthToken> token = (ResponseEntity<AuthToken>)authController.login(login);

        session.setAttribute("username", login.getUsername());
        session.setAttribute("token", token.getBody().getToken());
        model.addAttribute("posts", postRepository.findAll());

       return "redirect:/home";

    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String information(HttpSession session, Model model) {
        String username = session.getAttribute("username").toString();
        if (username != null){
            User user = userReposity.findByUsername(username);

            session.setAttribute("username", "username: " + session.getAttribute("username"));
            session.setAttribute("fullName", "full_Name: " + user.getFullName());
            session.setAttribute("email", "Email: " + user.getEmail());
            return "info";
        }
        else return "redirect:/login";

    }

}
