package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dao.CountryRepository;
import com.senlainc.advertisementsystem.dto.address.country.city.CityDto;
import com.senlainc.advertisementsystem.dto.address.country.city.CityUpdateDto;
import com.senlainc.advertisementsystem.model.address.country.city.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CityMapper extends AbstractMapper<City, CityDto, CityUpdateDto> {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CityMapper(CountryRepository countryRepository, ModelMapper modelMapper) {
        super(modelMapper, City.class, CityDto.class);
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(CityUpdateDto.class, City.class)
                .addMappings(m -> {
                    m.skip(City::setCountry);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapFields(City source, CityDto destination) {
    }

    @Override
    protected void mapFields(CityUpdateDto source, City destination) {
        if (source.getCountryId() != null) {
            destination.setCountry(countryRepository.getOne(source.getCountryId()));
        }
    }
}
