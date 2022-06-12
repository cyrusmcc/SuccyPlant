package com.cm.contentmanagementapp.payload.response;

import java.time.LocalDate;

public class UserInfoResponse {

    private String username;

    private LocalDate joinDate;

    private Boolean hasProfileImage;

    public UserInfoResponse(String username, LocalDate joinDate, Boolean hasProfileImage) {
        this.username = username;
        this.joinDate = joinDate;
        this.hasProfileImage = hasProfileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public Boolean getHasProfileImage() {
        return hasProfileImage;
    }

    public void setHasProfileImage(Boolean hasProfileImage) {
        this.hasProfileImage = hasProfileImage;
    }
}
