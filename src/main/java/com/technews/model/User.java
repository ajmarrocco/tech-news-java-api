package com.technews.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import javax.xml.stream.events.Comment;
import java.util.List;

// marks User class as persistable object so it can map to a table
@Entity
// Specifies properties that should be ignored when serializing this object to JSON
// arguments are the properties that should be ignored
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// specifies name of table the class maps too
@Table(name = "user")
public class User {
    // will be unique identifier
    @Id
    // will be a generated value
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    // value must be unique
    @Column(unique = true)
    private String email;
    private String password;
    // This data will NOT be persisted in database
    @Transient
    boolean loggedIn;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Post> posts;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vote> votes;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;

}
