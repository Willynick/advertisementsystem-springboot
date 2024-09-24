package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dao.AddressRepository;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileDto;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileUpdateDto;
import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProfileMapper extends AbstractMapper<Profile, ProfileDto, ProfileUpdateDto> {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ProfileMapper(AddressRepository addressRepository, AddressMapper addressMapper, ModelMapper modelMapper) {
        super(modelMapper, Profile.class, ProfileDto.class);
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Profile.class, ProfileDto.class)
                .addMappings(m -> {
                    m.skip(ProfileDto::setUserId);
                }).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(ProfileUpdateDto.class, Profile.class)
                .addMappings(m -> {
                    m.skip(Profile::setAddress);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapFields(Profile source, ProfileDto destination) {
        destination.setUserId(source.getUser().getId());
    }

    @Override
    protected void mapFields(ProfileUpdateDto source, Profile destination) {
        Address address = source.getAddress().getId() != null
                ? addressRepository.getOne(source.getAddress().getId())
                : getAddress(addressMapper.toEntity(source.getAddress()), destination);
        address.setProfile(destination);
        destination.setAddress(address);
    }

    private Address getAddress(Address address, Profile profile) {
        address.setProfile(profile);
        return address;
    }
}
