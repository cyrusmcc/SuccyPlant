package com.cm.contentmanagementapp.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtResponse(String accessToken, String refreshToken, long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
