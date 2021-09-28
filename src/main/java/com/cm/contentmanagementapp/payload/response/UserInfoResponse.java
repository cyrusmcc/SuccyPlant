package com.cm.contentmanagementapp.payload.response;

import org.springframework.core.io.Resource;

import java.time.LocalDate;

public class UserInfoResponse {

    private String username;

    private LocalDate joinDate;

    public UserInfoResponse(String username, LocalDate joinDate) {
        this.username = username;
        this.joinDate = joinDate;
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
