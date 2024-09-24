package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.category.CategoryDto;
import com.senlainc.advertisementsystem.dto.category.CategoryUpdateDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CategoryService {

    CategoryDto add(CategoryUpdateDto categoryDto);
    CategoryDto update(Long id, CategoryUpdateDto categoryDto);
    CategoryDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters);
    Boolean delete(Long id);
    CategoryDto getById(Long id);
    List<CategoryDto> get(List<ViewSortParameter> parameters);
    CategoryDto getParent(Long id);
    List<CategoryDto> getChild(Long id, List<ViewSortParameter> parameters);
    CategoryDto getByAdvertisement(Long advertisementId);
}
