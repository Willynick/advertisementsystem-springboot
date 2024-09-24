package com.senlainc.advertisementsystem.validatorapi;

import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;

public interface Validator<T extends AbstractModel> {

    void checkNotNull(T obj);
    void checkIsExistsEntity(T obj);
}
