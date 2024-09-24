package com.senlainc.advertisementsystem.dto.address.country.city;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityUpdateDto {

    @NotEmpty(message = "Please provide a name")
    private String name;
    private Long countryId;
}
