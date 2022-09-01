package com.technews.controller;

import com.technews.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentRepository repository;

    @GetMapping("/api/comments")
    public List<Comment> getAllComments(){
        return repository.findAll();
    }

    @GetMapping("/api/comments/{id}")
    public Comment getComment(@PathVariable int id){
        return repository.getReferenceById(id);
    }
}
