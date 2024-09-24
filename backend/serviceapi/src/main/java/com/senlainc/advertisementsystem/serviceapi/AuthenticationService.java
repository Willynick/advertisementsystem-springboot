package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.dto.authentication.AuthenticationDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity login(AuthenticationDto authenticationDto);
}
