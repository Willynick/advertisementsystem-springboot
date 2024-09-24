package com.senlainc.advertisementsystem.dto.address.country.city;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.dto.address.country.CountryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto extends AbstractDto {

    private String name;
    //private CountryDto country;
}
