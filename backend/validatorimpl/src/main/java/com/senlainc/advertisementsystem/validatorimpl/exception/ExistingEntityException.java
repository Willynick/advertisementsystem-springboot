package com.senlainc.advertisementsystem.validatorimpl.exception;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ExistingEntityException extends RuntimeException {

    public ExistingEntityException(String objectType) {
        super(String.format(Constants.EXISTING_ENTITY_MESSAGE, objectType));
    }
}
