package com.senlainc.advertisementsystem.dto.user.role.permission;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class PermissionDto extends AbstractDto {

    private String name;
}
