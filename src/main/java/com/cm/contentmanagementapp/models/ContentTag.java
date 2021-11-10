package com.cm.contentmanagementapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ContentTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long id;

    private String value;

    private EnumTagCategory category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="post_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> posts;

    public ContentTag() {
    }

    public ContentTag(String value, EnumTagCategory category) {
        this.value = value;
        this.category = category;
        posts = new HashSet<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public EnumTagCategory getCategory() {
        return category;
    }

    public void setCategory(EnumTagCategory category) {
        this.category = category;
    }

    @JsonBackReference
    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }
}
