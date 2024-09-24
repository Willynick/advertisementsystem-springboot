package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.model.address.country.Country;
import com.senlainc.advertisementsystem.validatorapi.CountryValidator;
import org.springframework.stereotype.Component;

@Component
public class CountryValidatorImpl extends AbstractValidator<Country> implements CountryValidator {

    protected CountryValidatorImpl() {
        super("Country");
    }
}
