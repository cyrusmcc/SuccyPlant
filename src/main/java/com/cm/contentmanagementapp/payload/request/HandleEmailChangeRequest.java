package com.cm.contentmanagementapp.payload.request;

import javax.validation.constraints.NotBlank;

public class HandleEmailChangeRequest {

    @NotBlank
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
