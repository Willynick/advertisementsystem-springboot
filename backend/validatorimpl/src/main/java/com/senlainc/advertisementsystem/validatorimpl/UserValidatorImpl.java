package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.validatorapi.UserValidator;
import org.springframework.stereotype.Component;

@Component
public class UserValidatorImpl extends AbstractValidator<User> implements UserValidator {

    protected UserValidatorImpl() {
        super("User");
    }
}
