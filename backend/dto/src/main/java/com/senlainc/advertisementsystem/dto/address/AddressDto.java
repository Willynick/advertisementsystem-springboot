package com.senlainc.advertisementsystem.dto.address;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.dto.address.country.city.CityDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class AddressDto extends AbstractDto {

    private String address;
    private CityDto city;
}
