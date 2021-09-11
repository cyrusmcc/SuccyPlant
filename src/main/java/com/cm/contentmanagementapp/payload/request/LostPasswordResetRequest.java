package com.cm.contentmanagementapp.payload.request;

import javax.validation.constraints.NotBlank;

/* Used for changing forgotten password (login screen)  */
public class LostPasswordResetRequest {

    @NotBlank
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
