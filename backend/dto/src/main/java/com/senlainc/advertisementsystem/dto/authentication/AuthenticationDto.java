package com.senlainc.advertisementsystem.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {

    @NotEmpty(message = "Please provide a username")
    private String username;
    @NotEmpty(message = "Please provide a password")
    private String password;
}
