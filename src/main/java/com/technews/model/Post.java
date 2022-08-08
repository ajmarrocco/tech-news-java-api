package com.technews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

// marks Post class as persistable object so it can map to a table
@Entity
// Specifies properties that should be ignored when serializing this object to JSON
// arguments are the properties that should be ignored
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// specifies name of table the class maps too
@Table(name = "post")

public class Post implements Serializable {
    // will be unique identifier
    @Id
    // will be a generated value
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String title;
    private String posturl;
    // This data will NOT be persisted in database
    @Transient
    private String username;
    // This data will NOT be persisted in database
    @Transient
    private int voteCount;
    private Integer userId;
    private Date postedAt = new Date();
    private Date updatedAt = new Date();
    private List<Comment> comments;



}
