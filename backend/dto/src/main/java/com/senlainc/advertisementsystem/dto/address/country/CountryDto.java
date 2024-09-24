package com.senlainc.advertisementsystem.dto.address.country;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class CountryDto extends AbstractDto {

    private String name;
}
