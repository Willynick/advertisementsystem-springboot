package com.senlainc.advertisementsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dto.authentication.AuthenticationDto;
import com.senlainc.advertisementsystem.exceptionhandling.ControllerExceptionHandler;
import com.senlainc.advertisementsystem.serviceapi.AuthenticationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;
    @Mock
    private AuthenticationService authenticationService;
    @InjectMocks
    private ObjectMapper objectMapper;
    @InjectMocks
    private ControllerExceptionHandler controllerExceptionHandler;
    private MockMvc mockMvc;

    private static AuthenticationDto authenticationDto;

    @BeforeAll
    static void initialize() {
        authenticationDto = new AuthenticationDto("username", "password");
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
                .setControllerAdvice(controllerExceptionHandler)
                .build();
    }

    @Test
    void login() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "auth/login")
                    .content(objectMapper.writeValueAsString(authenticationDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(authenticationService, atLeastOnce()).login(authenticationDto);
    }

    @Test
    void loginFail() {
        try {
            mockMvc.perform(post(Constants.BASE_URL + "auth")
                    .content(objectMapper.writeValueAsString(authenticationDto))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(404));
        } catch (Exception e) {
            e.printStackTrace();
        }

        verify(authenticationService, never()).login(authenticationDto);
    }
}