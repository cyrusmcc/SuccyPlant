package com.cm.contentmanagementapp.models;

import javax.persistence.*;

@Entity
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_id")
    private Long id;

    @Column(name = "common_name")
    private String commonName;

    @Column(name = "science_name")
    private String scientificName;

    private String descTextFileUrl;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    public Plant() {
        this.post = new Post();
        this.post.setPostType(PostType.GALLERY);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public Post getPost() {
        return post;
    }

    public void setTitle(String title) {
        this.post.setTitle(title);
    }

    public void setAuthor(String author) {
        this.post.setAuthorUsername(author);
    }

    public void setPostImageId(String imageId) {
        this.post.setImageId(imageId);
    }

    public void setPostList(PostList list) {
        this.post.setPostList(list);
    }

}
