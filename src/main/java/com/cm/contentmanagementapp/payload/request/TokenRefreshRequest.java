package com.cm.contentmanagementapp.payload.request;

import javax.validation.constraints.NotBlank;

public class TokenRefreshRequest {

    @NotBlank(message = "Invalid request")
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
