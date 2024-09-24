package com.senlainc.advertisementsystem.dto.user.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import com.senlainc.advertisementsystem.dto.address.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto extends AbstractDto {

    private Long userId;
    private String firstName;
    private String secondName;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private boolean gender;
    private double amountOfMoney;
    private AddressDto address;
    private double averageRating;
}
