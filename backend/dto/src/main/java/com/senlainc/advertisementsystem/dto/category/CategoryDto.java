package com.senlainc.advertisementsystem.dto.category;

import com.senlainc.advertisementsystem.dto.abstractdto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
    public class CategoryDto extends AbstractDto {

    private String name;
    private Long parentCategoryId;
}
