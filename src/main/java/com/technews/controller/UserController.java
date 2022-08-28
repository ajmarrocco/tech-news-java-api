package com.technews.controller;

import com.technews.repository.UserRepository;
import com.technews.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    // Autowired is similar to "new" except it only instantiates objects as needed
    @Autowired
    UserRepository repository;
    @Autowired
    VoteRepository voteRepository;
}
