package com.senlainc.advertisementsystem.dto.user;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.model.user.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto extends AbstractDto {

    private String username;
    private Long profileId;
    private String status;
    private Language language;
}
