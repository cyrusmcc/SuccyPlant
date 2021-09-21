package com.cm.contentmanagementapp.payload.request;

import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.NotNull;

public class ProfilePictureChangeRequest {

    @NotNull
    Long id;

    @NotNull
    MultipartFile file;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
