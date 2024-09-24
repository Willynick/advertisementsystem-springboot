package com.senlainc.advertisementsystem.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdateDto {

    @NotEmpty(message = "Please provide a name")
    private String name;
    private Long parentCategoryId;
}
