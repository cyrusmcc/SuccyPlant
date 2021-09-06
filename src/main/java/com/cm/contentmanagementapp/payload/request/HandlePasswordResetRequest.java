package com.cm.contentmanagementapp.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class HandlePasswordResetRequest {

    @NotBlank
    String token;

    @NotBlank
    String password;

}
