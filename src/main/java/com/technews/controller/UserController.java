package com.technews.controller;

import com.technews.model.Post;
import com.technews.model.User;
import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    // Autowired is similar to "new" except it only instantiates objects as needed
    @Autowired
    UserRepository repository;
    @Autowired
    VoteRepository voteRepository;

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        // calls inherited findAll() method and assigns to list variable
        List<User> userList = repository.findAll();
        for (User u : userList) {
            // Get all the different users' posts
            List<Post> postList = u.getPosts();
            for (Post p : postList) {
                // Gets all the posts by post Id number and gives each post a number
                p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
            }
        }
        return userList;
    }

    @GetMapping("/api/users/{id}")
    public User getUserById(@PathVariable Integer id) {
        // Use getReferenceById instead of getById because it is deprecated
        User returnUser = repository.getReferenceById(id);
        // Gets all posts from one user
        List<Post> postList = returnUser.getPosts();
        for (Post p : postList) {
            // Gets all the posts by post Id number and gives each post a number
            p.setVoteCount(voteRepository.countVotesByPostId(p.getId()));
        }
        return returnUser;
    }

    @PostMapping("/api/users")
    // @RequestBody annotation, maps body of request into transfer object
    // Deserializes body (bytestream) onto a Java object
    // similar to req.body in JS
    public User addUser(@RequestBody User user) {
        // Encrypt password
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        repository.save(user);
        return user;
    }


    @PutMapping("/api/users/{id}")
    // @PathVariable allows to enter the int id into request URI as parameter
    // replacing the /{id}
    // similar to id: req.params.id in JS
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        User tempUser = repository.getReferenceById(id);
        if (!tempUser.equals(null)) {
            user.setId(tempUser.getId());
            repository.save(user);
        }
        return user;
    }

    @DeleteMapping("/api/users/{id}")
    // Sets status code of HTTP response
    // NO_CONTENT yields 204 message which means
    // server has successfully fulfilled the request
    @ResponseStatus(HttpStatus.NO_CONTENT)
    // @PathVariable allows to enter the int id into request URI as parameter
    // replacing the /{id}
    // similar to id: req.params.id in JS
    public void deleteUser(@PathVariable int id) {
        repository.deleteById(id);
    }
}
