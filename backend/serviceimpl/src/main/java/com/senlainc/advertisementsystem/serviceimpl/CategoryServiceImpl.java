package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.CategoryRepository;
import com.senlainc.advertisementsystem.daospec.CategorySpecification;
import com.senlainc.advertisementsystem.dto.category.CategoryDto;
import com.senlainc.advertisementsystem.dto.category.CategoryUpdateDto;
import com.senlainc.advertisementsystem.mapper.CategoryMapper;
import com.senlainc.advertisementsystem.model.category.Category;
import com.senlainc.advertisementsystem.model.category.Category_;
import com.senlainc.advertisementsystem.serviceapi.CategoryService;
import com.senlainc.advertisementsystem.validatorapi.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CategoryValidator categoryValidator;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper,
                               CategoryValidator categoryValidator) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.categoryValidator = categoryValidator;
    }

    @Override
    public CategoryDto add(CategoryUpdateDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CategoryUpdateDto categoryDto) {
        Category category = categoryRepository.getOne(id);
        categoryValidator.checkNotNull(category);

        categoryMapper.map(categoryDto, category);
        categoryRepository.save(category);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters) {
        categoryRepository.partiallyUpdate(Category_.id, id, Converter.convertUpdateParameter(Category_.class, parameters));
        return categoryMapper.toDto(categoryRepository.getOne(id));
    }

    @Override
    public Boolean delete(Long id) {
        Category category = categoryRepository.getOne(id);
        categoryValidator.checkNotNull(category);

        categoryRepository.delete(category);
        return true;
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.getOne(id);
        categoryValidator.checkNotNull(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> get(List<ViewSortParameter> parameters) {
        return categoryMapper.toDtoList(
                categoryRepository.findAll(Converter.convertSortParameter(parameters)));
    }

    @Override
    public CategoryDto getParent(Long id) {
        Category category = categoryRepository.getParentCategoryById(id);
        categoryValidator.checkNotNull(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> getChild(Long id, List<ViewSortParameter> parameters) {
        return categoryMapper.toDtoList(
                categoryRepository.getByParentCategoryId(id, Converter.convertSortParameter(parameters)));
    }

    @Override
    public CategoryDto getByAdvertisement(Long advertisementId) {
        Category category = categoryRepository.findOne(CategorySpecification.getByAdvertisementId(advertisementId)).get();
        categoryValidator.checkNotNull(category);

        return categoryMapper.toDto(category);
    }
}
