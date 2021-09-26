package com.cm.contentmanagementapp.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/* Used for changing password while already authenticated (settings page)  */
public class PasswordResetRequest {

    @NotBlank
    String currentPassword;

    @NotBlank
    String newPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
