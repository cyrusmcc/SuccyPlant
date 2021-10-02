package com.cm.contentmanagementapp.models;

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

    @OneToMany(mappedBy = "postList", cascade = {CascadeType.ALL})
    private List<Post> userPosts;

    public Long getPostListId() {
        return postListId;
    }

    public void setPostListId(Long postListId) {
        this.postListId = postListId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /*
    public List<Post> getUserPosts() {
        return userPosts;
    }

    public void setUserPosts(List<Post> userPosts) {
        this.userPosts = userPosts;
    }
     */
}
