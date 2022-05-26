package com.cm.contentmanagementapp.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="filePath")
    private String filePath;

    @Column(name="image_id")
    private String fileName;

    @Column(name="author")
    private String author;

    @Column(name="upload_date")
    private LocalDate uploadDate;

    public Image() {
    }

    public Image(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getfileName() {
        return fileName;
    }

    public void setfileName(String imageId) {
        this.fileName = imageId;
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
