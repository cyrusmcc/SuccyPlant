package com.cm.contentmanagementapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> Roles;

    public JwtResponse(String jwt, Long id, String username, String email, List<String> roles) {
    }
}
