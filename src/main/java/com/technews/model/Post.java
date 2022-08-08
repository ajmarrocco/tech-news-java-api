package com.technews.model;

import java.io.Serializable;

public class Post implements Serializable {
    private Integer id;
    private String title;
    private String posturl;
    private String username;
    private int voteCount;
    private Integer userId;
    private Date postedAt = new Date();
    private Date updatedAt = new Date();
    private List<Comment> comments;
}
