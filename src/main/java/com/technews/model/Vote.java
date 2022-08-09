package com.technews.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

// marks Vote class as persistable object so it can map to a table
@Entity
// Specifies properties that should be ignored when serializing this object to JSON
// arguments are the properties that should be ignored
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// specifies name of table the class maps too
@Table(name = "comment")
public class Vote implements Serializable {
    private Integer id;
    private Integer userId;
    private Integer postId;
}
