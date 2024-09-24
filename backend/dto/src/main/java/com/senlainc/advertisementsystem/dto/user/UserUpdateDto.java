package com.senlainc.advertisementsystem.dto.user;

import com.senlainc.advertisementsystem.model.user.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {

    @NotEmpty(message = "Please provide a username")
    private String username;
    @NotEmpty(message = "Please provide a password")
    private String password;
    private Language language;
}
