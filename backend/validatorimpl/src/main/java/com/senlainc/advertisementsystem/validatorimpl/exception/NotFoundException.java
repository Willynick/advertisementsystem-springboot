package com.senlainc.advertisementsystem.validatorimpl.exception;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(String objectType) {
        super(String.format(Constants.OBJECT_IS_NOT_FIND, objectType));
    }
}
