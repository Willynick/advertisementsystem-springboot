package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.model.address.country.city.City;
import com.senlainc.advertisementsystem.validatorapi.CityValidator;
import org.springframework.stereotype.Component;

@Component
public class CityValidatorImpl extends AbstractValidator<City> implements CityValidator {

    protected CityValidatorImpl() {
        super("City");
    }
}
