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

    @Column(name="postDate")
    private LocalDate postDate;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    public BlogPost() {
        this.post = new Post();
        this.post.setPostType(PostType.BLOG);
        this.setPostDate(LocalDate.now());
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

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

}
