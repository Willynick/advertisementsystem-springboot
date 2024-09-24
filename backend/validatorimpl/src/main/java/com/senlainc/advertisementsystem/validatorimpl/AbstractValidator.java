package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import com.senlainc.advertisementsystem.validatorapi.Validator;
import com.senlainc.advertisementsystem.validatorimpl.exception.ExistingEntityException;
import com.senlainc.advertisementsystem.validatorimpl.exception.NotFoundException;

public abstract class AbstractValidator<T extends AbstractModel> implements Validator<T> {

    private final String objectType;

    protected AbstractValidator(String objectType) {
        this.objectType = objectType;
    }

    @Override
    public void checkNotNull(T obj) {
        if (obj == null) {
            throw new NotFoundException(objectType);
        }
    }

    @Override
    public void checkIsExistsEntity(T obj) {
        if (obj != null) {
            throw new ExistingEntityException(objectType);
        }
    }
}
