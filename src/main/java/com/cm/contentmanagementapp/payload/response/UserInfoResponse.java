package com.cm.contentmanagementapp.payload.response;

import org.springframework.core.io.Resource;

import java.time.LocalDate;

public class UserInfoResponse {

    private Resource profilePic;

    private String username;

    private LocalDate joinDate;

    public UserInfoResponse(Resource profilePic, String username, LocalDate joinDate) {
        this.profilePic = profilePic;
        this.username = username;
        this.joinDate = joinDate;
    }

    public Resource getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Resource profilePic) {
        this.profilePic = profilePic;
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
}
