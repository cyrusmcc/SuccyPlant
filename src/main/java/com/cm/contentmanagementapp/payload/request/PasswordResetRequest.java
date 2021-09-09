package com.cm.contentmanagementapp.payload.request;

import javax.validation.constraints.NotBlank;

public class PasswordResetRequest {

    @NotBlank
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
