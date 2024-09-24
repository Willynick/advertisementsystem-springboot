package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.dto.category.CategoryDto;
import com.senlainc.advertisementsystem.serviceapi.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable("id") Long id) {
        return categoryService.getById(id);
    }

    @GetMapping
    public List<CategoryDto> get(@RequestBody List<ViewSortParameter> parameters) {
        return categoryService.get(parameters);
    }

    @GetMapping("/{id}/parent")
    public CategoryDto getParent(@PathVariable("id") Long id) {
        return categoryService.getParent(id);
    }

    @GetMapping("/{id}/child")
    public List<CategoryDto> getChild(@PathVariable("id") Long id, @RequestBody List<ViewSortParameter> parameters) {
        return categoryService.getChild(id, parameters);
    }

    @GetMapping("/advertisement/{id}")
    public CategoryDto getByAdvertisement(@PathVariable("id") Long advertisementId) {
        return categoryService.getByAdvertisement(advertisementId);
    }
}
