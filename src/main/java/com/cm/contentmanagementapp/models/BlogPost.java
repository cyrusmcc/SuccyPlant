package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    private String bodyTextFileUrl;

    @Column(name="postDate")
    private LocalDate postDate;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    public BlogPost() {
        this.post = new Post();
        this.post.setPostType(PostType.BLOG);
        this.bodyTextFileUrl = UUID.randomUUID()+ ".md";
        this.setPostDate(LocalDate.now());
    }

    public Long getId() {
        return id;
    }

    public String getBodyTextFileId() {
        return bodyTextFileUrl;
    }

    public void setBodyTextFileId(String bodyTextFileId) {
        this.bodyTextFileUrl = bodyTextFileId;
    }

    public Post getPost() {
        return post;
    }

    public void setPostTitle(String title) {
        this.post.setTitle(title);
    }

    public void setPostAuthor(String author) {
        this.post.setAuthorUsername(author);
    }

    public void setPostImageId(String imageId) {
        this.post.setImageId(imageId);
    }

    public void setPostList(PostList list) {
        this.post.setPostList(list);
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }

}
