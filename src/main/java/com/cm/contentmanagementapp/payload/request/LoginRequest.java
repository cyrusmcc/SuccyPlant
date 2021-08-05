package com.cm.contentmanagementapp.payload.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @Size(min = 3, max = 14)
    private String username;

    @NotBlank
    @Size(min = 6, max = 128)
    private String password;

    private String email;

}
