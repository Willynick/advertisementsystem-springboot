package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.validatorapi.ProfileValidator;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidatorImpl extends AbstractValidator<Profile> implements ProfileValidator {

    public ProfileValidatorImpl() {
        super("Profile");
    }
}
