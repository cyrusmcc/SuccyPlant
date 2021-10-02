package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    private String bodyText;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    public BlogPost() {
        this.post = new Post();
        this.post.setPostType(PostType.BLOG);
        this.post.setPostDate(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public String getBodyText() {
        return bodyText;
    }

    public void setBodyText(String bodyText) {
        this.bodyText = bodyText;
    }

    public Post getPost() {
        return post;
    }

    public Long getPostId() {
        return this.post.getId();
    }

    public String getAuthorUsername() {
        return this.post.getAuthorUsername();
    }

    public void setAuthorUsername(String authorUsername) {
        this.post.setAuthorUsername(authorUsername);
    }

    public LocalDate getPostDate() {
        return this.post.getPostDate();
    }

    public void setImageIds(List<String> imageIds) {
        this.post.setImageIds(imageIds);
    }

    public List<String> getImageIds() {
        return this.post.getImageIds();
    }

    public PostList getPostList() {
        return this.post.getPostList();
    }


}
