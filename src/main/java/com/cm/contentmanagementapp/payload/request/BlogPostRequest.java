package com.cm.contentmanagementapp.payload.request;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BlogPostRequest {

    @NotBlank
    String title;

    @NotBlank
    String authorUsername;

    @NotNull
    MultipartFile image;

    @NotNull
    MultipartFile bodyText;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getBodyText() {
        return bodyText;
    }

    public void setBodyText(MultipartFile bodyText) {
        this.bodyText = bodyText;
    }
}
