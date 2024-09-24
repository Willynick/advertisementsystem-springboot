package com.senlainc.advertisementsystem.mapper;

import com.senlainc.advertisementsystem.dao.CategoryRepository;
import com.senlainc.advertisementsystem.dto.category.CategoryDto;
import com.senlainc.advertisementsystem.dto.category.CategoryUpdateDto;
import com.senlainc.advertisementsystem.model.category.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CategoryMapper extends AbstractMapper<Category, CategoryDto, CategoryUpdateDto> {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryMapper(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        super(modelMapper, Category.class, CategoryDto.class);
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Category.class, CategoryDto.class)
                .addMappings(m -> {
                    m.skip(CategoryDto::setParentCategoryId);
                }).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(CategoryUpdateDto.class, Category.class)
                .addMappings(m -> {
                    m.skip(Category::setParentCategory);
                }).setPostConverter(toEntityConverter());
    }

    @Override
    protected void mapFields(Category source, CategoryDto destination) {
        if (source.getParentCategory() != null) {
            destination.setParentCategoryId(source.getParentCategory().getId());
        }
    }

    @Override
    protected void mapFields(CategoryUpdateDto source, Category destination) {
        if (source.getParentCategoryId() != null) {
            destination.setParentCategory(categoryRepository.getOne(source.getParentCategoryId()));
        }
    }
}
