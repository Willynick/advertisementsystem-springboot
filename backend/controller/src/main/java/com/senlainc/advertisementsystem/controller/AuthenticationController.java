package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.dto.authentication.AuthenticationDto;
import com.senlainc.advertisementsystem.serviceapi.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticationDto authenticationDto) {
        return authenticationService.login(authenticationDto);
    }
}
