package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name = "genus")
    private String genus;

    @Column(name = "type")
    private String type;

    @Column(name = "size_desc", columnDefinition = "TEXT")
    private String sizeDesc;

    @Column(name = "water_desc", columnDefinition = "TEXT")
    private String waterDesc;

    @Column(name = "light_desc", columnDefinition = "TEXT")
    private String lightDesc;

    @Column(name = "pet_desc", columnDefinition = "TEXT")
    private String petDesc;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;

    public Plant() {
        this.post = new Post();
        this.post.setPostType(PostType.PLANT);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSizeDesc() {
        return sizeDesc;
    }

    public void setSizeDesc(String sizeDesc) {
        this.sizeDesc = sizeDesc;
    }

    public String getWaterDesc() {
        return waterDesc;
    }

    public void setWaterDesc(String waterDesc) {
        this.waterDesc = waterDesc;
    }

    public String getLightDesc() {
        return lightDesc;
    }

    public void setLightDesc(String lightDesc) {
        this.lightDesc = lightDesc;
    }

    public String getPetDesc() {
        return petDesc;
    }

    public void setPetDesc(String petDesc) {
        this.petDesc = petDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Post getPost() {
        return post;
    }

    public Set<ContentTag> getPostTags() {
        return post.getTags();
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
