package com.senlainc.advertisementsystem.dto.user.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senlainc.advertisementsystem.dto.address.AddressUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileUpdateDto {

    private String firstName;
    private String secondName;
    @Email
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    private boolean gender;
    private AddressUpdateDto address;
}
