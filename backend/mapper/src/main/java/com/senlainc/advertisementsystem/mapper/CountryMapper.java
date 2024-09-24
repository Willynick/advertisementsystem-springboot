package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.address.country.CountryDto;
import com.senlainc.advertisementsystem.dto.address.country.CountryUpdateDto;
import com.senlainc.advertisementsystem.model.address.country.Country;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper extends AbstractMapper<Country, CountryDto, CountryUpdateDto> {

    @Autowired
    public CountryMapper(ModelMapper modelMapper) {
        super(modelMapper, Country.class, CountryDto.class);
    }

    @Override
    protected void mapFields(Country source, CountryDto destination) {

    }

    @Override
    protected void mapFields(CountryUpdateDto source, Country destination) {

    }
}
