package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.backendutils.GetParameter;
import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.backendutils.UpdateParameter;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

public interface GenericDao <T, PK extends Serializable> {

    void create(T object);
    void update(T object);
    void partiallyUpdate(SingularAttribute pkAttribute, PK pkValue, List<UpdateParameter> updateParameters);
    void delete(T object);
    T getByPK(PK id);
    List<T> getAll(List<SortParameter> sortParameters);
    List<T> getByParameters(List<GetParameter> parameters);
}
