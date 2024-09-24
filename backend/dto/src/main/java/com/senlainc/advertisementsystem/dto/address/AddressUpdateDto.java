package com.senlainc.advertisementsystem.dto.address;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressUpdateDto extends AbstractDto {

    @NotEmpty(message = "Please provide a name")
    private String address;
    private Long cityId;
}
