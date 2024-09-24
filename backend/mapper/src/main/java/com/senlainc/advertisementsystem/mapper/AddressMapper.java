package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dao.CityRepository;
import com.senlainc.advertisementsystem.dto.address.AddressDto;
import com.senlainc.advertisementsystem.dto.address.AddressUpdateDto;
import com.senlainc.advertisementsystem.model.address.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddressMapper extends AbstractMapper<Address, AddressDto, AddressUpdateDto> {

    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AddressMapper(CityRepository cityRepository, ModelMapper modelMapper) {
        super(modelMapper, Address.class, AddressDto.class);
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(AddressUpdateDto.class, Address.class)
                .addMappings(m -> {
                    m.skip(Address::setCity);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapFields(Address source, AddressDto destination) {

    }

    @Override
    protected void mapFields(AddressUpdateDto source, Address destination) {
        destination.setCity(cityRepository.getOne(source.getCityId()));
    }
}
