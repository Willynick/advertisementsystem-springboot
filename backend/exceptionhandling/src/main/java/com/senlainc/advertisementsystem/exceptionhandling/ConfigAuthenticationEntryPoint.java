package com.senlainc.advertisementsystem.exceptionhandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class ConfigAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String CONTENT_TYPE = "application/json; charset=UTF-8";

    private final ObjectMapper objectMapper;

    @Autowired
    public ConfigAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType(CONTENT_TYPE);
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                new ErrorResponse(HttpStatus.FORBIDDEN, Constants.ACCESS_DENIED, new ArrayList())));
    }
}
