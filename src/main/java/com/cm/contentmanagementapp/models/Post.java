package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    private String imageId;

    @ManyToMany
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")

    )
    private Set<ContentTag> contentTags;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_list_id")
    private PostList postList;

    public Post() {
        this.contentTags = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public PostList getPostList() {
        return postList;
    }

    public void setPostList(PostList postList) {
        this.postList = postList;
    }

    public Set<ContentTag> getTags() {
        return contentTags;
    }

    public void setTags(Set<ContentTag> contentTags) {
        this.contentTags = contentTags;
    }
}
