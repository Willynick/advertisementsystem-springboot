package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.backendutils.GetParameter;
import com.senlainc.advertisementsystem.backendutils.UpdateParameter;
import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository <T extends AbstractModel, ID extends Serializable> extends JpaRepository<T, ID> {

    void partiallyUpdate(SingularAttribute pkAttribute, ID pkValue, List<UpdateParameter> updateParameters);
    List<T> getByParameters(List<GetParameter> parameters);
}