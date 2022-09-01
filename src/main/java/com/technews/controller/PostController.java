package com.technews.controller;

import com.technews.model.Post;
import com.technews.model.User;
import com.technews.model.Vote;
import com.technews.repository.PostRepository;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostRepository repository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/posts")
    public List<Post> getAllPosts() {
        List<Post> postList = repository.findAll();
        for (Post p : postList) {
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }
        return postList;
    }


    @GetMapping("/api/posts/{id}")
    public Post getPost(@PathVariable Integer id) {
        Post returnPost = repository.getReferenceById(id);
        returnPost.setVoteCount(voteRepository.countVotesByPostId(returnPost.getId()));

        return returnPost;
    }


    @PostMapping("/api/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post addPost(@RequestBody Post post) {
        repository.save(post);
        return post;
    }


    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
        Post tempPost = repository.getReferenceById(id);
        tempPost.setTitle(post.getTitle());
        return repository.save(tempPost);
    }


    @PutMapping("/api/posts/upvote")
    // HttpServletRequest provides request information for HTTP servlets
    // HTTP servlet a special type of servlet that handles an HTTP request
    // and provides an HTTP response, usually in the form of an HTML page.

    // @RequestBody annotation, maps body of request into transfer object
    // Deserializes body (bytestream) onto a Java object
    // similar to req.body in JS
    public String addVote(@RequestBody Vote vote, HttpServletRequest request) {
        String returnValue = "";
        // getSession(false) retrieves current session
        // If it does not exist, return null
        // If getSession(false) does not equal null, then session exists
        if(request.getSession(false) != null) {
            Post returnPost = null;
            // Gets user object from the session
            // getAttribute() fetches data from database
            User sessionUser = (User) request.getSession().getAttribute("SESSION_USER");
            // sessionUser.getId() gets the id from the User in session
            // It then uses that id number to set the User id for the vote
            vote.setUserId(sessionUser.getId());
            voteRepository.save(vote);

            returnPost = repository.getReferenceById(vote.getPostId());
            returnPost.setVoteCount(voteRepository.countVotesByPostId(vote.getPostId()));

            returnValue = "";
        } else {
            returnValue = "login";
        }

        return returnValue;
    }


    @DeleteMapping("/api/posts/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable int id) {
        repository.deleteById(id);
    }
}