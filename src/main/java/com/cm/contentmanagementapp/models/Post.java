package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

enum PostType {
    BLOG,
}


@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="authUsername")
    private String authorUsername;

    @Column(name="type")
    private PostType type;

    @ElementCollection
    private List<String> imageIds;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_list_id")
    private PostList postList;

    public Post() {
        imageIds = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    public PostType getPostType() {
        return type;
    }

    public void setPostType(PostType type) {
        this.type = type;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public PostList getPostList() {
        return postList;
    }

    public void setPostList(PostList postList) {
        this.postList = postList;
    }
}
