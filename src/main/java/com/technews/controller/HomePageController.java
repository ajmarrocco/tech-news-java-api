package com.technews.controller;

import com.technews.model.Post;
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
import java.util.List;

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

    @GetMapping("/")
    public String homepageSetup(Model model, HttpServletRequest request) {
        User sessionUser = new User();

        if (request.getSession(false) != null) {
            sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            model.addAttribute("loggedIn", sessionUser.isLoggedIn());
        } else {
            model.addAttribute("loggedIn", false);
        }

        // retrieves all posts
        List<Post> postList = postRepository.findAll();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            User user = userRepository.getReferenceById(p.getUserId());
            p.setUserName(user.getUsername());
        }

        model.addAttribute("postList", postList);
        model.addAttribute("loggedIn", sessionUser.isLoggedIn());

        // "point" and "points" attributes refer to upvotes.
        model.addAttribute("point", "point");
        model.addAttribute("points", "points");

        return "homepage";
    }

}
