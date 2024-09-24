package com.senlainc.advertisementsystem.controller.mocks;

import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.model.user.Language;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;

public class AuthenticationFactoryMock {

    private static Authentication authentication;

    private AuthenticationFactoryMock() {
    }

    public static Authentication getAuthentication() {
        if (authentication == null) {
            JwtUser jwtUser = new JwtUser(1L, "username", "password", Language.BEL, true, new ArrayList<>());
            authentication = new UsernamePasswordAuthenticationToken(jwtUser, "", jwtUser.getAuthorities());
        }
        return authentication;
    }
}
