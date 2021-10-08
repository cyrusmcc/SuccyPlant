package com.cm.contentmanagementapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class PostList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_list_id")
    private Long postListId;

    @OneToOne(mappedBy = "postList")
    private User user;

    public Long getPostListId() {
        return postListId;
    }

    public void setPostListId(Long postListId) {
        this.postListId = postListId;
    }

    @JsonBackReference
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
