package com.senlainc.advertisementsystem.dto.user.role;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.dto.user.role.permission.PermissionDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RoleDto extends AbstractDto {

    private String name;
    private List<PermissionDto> permissions = new ArrayList<>();
}
