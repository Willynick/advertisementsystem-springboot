package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dto.user.role.RoleDto;
import com.senlainc.advertisementsystem.model.user.role.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper extends AbstractMapper<Role, RoleDto, RoleDto> {

    @Autowired
    public RoleMapper(ModelMapper modelMapper) {
        super(modelMapper, Role.class, RoleDto.class);
    }

    @Override
    protected void mapFields(Role source, RoleDto destination) {

    }

    @Override
    protected void mapFields(RoleDto source, Role destination) {

    }
}
