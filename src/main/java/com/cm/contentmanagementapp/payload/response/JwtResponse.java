package com.cm.contentmanagementapp.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private String joinDate;

    public JwtResponse(String accessToken, String refreshToken, Long id, String username,
                       String email, LocalDate joinDate, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.joinDate = joinDate.toString();
    }
}
