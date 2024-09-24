package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.model.category.Category;
import com.senlainc.advertisementsystem.validatorapi.CategoryValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidatorImpl extends AbstractValidator<Category> implements CategoryValidator {

    @Autowired
    public CategoryValidatorImpl() {
        super("Category");
    }
}
