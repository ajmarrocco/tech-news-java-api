package com.technews.controller;

import com.technews.model.User;
import com.technews.repository.CommentRepository;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomePageController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    CommentRepository commentRepository;

    // calls login.html when login button is pressed
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request){
        if(request.getSession(false) != null) {
            // redirects to the homepage
            return "redirect:/";
        }
        // sends information to the Thymeleaf template
        // Sends newly created user to template as string "user"
        model.addAttribute("user", new User());
        return "login";

    }

    @GetMapping("/users/logout")
    public String logout(HttpServletRequest request){
        if(request.getSession(false)!=null){
            // if session exists, we invalidate session which logouts out user
            request.getSession().invalidate();
        }
        // redirects to login page
        return "redirect:/login";
    }

}
