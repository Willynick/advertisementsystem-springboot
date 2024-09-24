package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.user.UserDto;
import com.senlainc.advertisementsystem.dto.user.UserUpdateDto;
import com.senlainc.advertisementsystem.model.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserMapper extends AbstractMapper<User, UserDto, UserUpdateDto> {

    private final ModelMapper modelMapper;

    @Autowired
    UserMapper(ModelMapper modelMapper) {
        super(modelMapper, User.class, UserDto.class);
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .addMappings(m -> {
                    m.skip(UserDto::setProfileId);
                    m.skip(UserDto::setStatus);
                }).setPostConverter(toDtoConverter());
    }

    @Override
    protected void mapFields(User source, UserDto destination) {
        destination.setProfileId(source.getProfile().getId());
        destination.setStatus(source.getStatus().getName());
    }

    @Override
    protected void mapFields(UserUpdateDto source, User destination) {

    }
}
