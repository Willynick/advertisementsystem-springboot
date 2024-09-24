package com.senlainc.advertisementsystem.validatorimpl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
