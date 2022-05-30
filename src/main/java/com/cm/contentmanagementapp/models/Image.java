package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="img_path")
    private String imgPath;

    @Column(name="img_id")
    private String imgName;

    @Column(name="thumbnail_path")
    private String thumbnailPath;

    @Column(name="thumbnail_id")
    private String thumbnailName;

    @Column(name="author")
    private String author;

    @Column(name="upload_date")
    private LocalDate uploadDate;

    public Image() {
    }

    public Image(String imgname) {
        this.imgName = imgName;
    }

    public Long getId() {
        return id;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDate uploadDate) {
        this.uploadDate = uploadDate;
    }
}
