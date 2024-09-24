package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.backendutils.GetParameter;
import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.UpdateParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.BaseRepository;
import com.senlainc.advertisementsystem.daospec.exception.DatabaseException;
import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public class BaseRepositoryImpl <T extends AbstractModel, ID extends Serializable> extends SimpleJpaRepository<T, ID>
        implements BaseRepository<T, ID> {

    private final EntityManager entityManager;
    private final Class<T> domainClass;

    @Autowired
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.domainClass = getDomainClass();
    }

    @Override
    public void partiallyUpdate(SingularAttribute pkAttribute, ID pkValue, List<UpdateParameter> updateParameters) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> cu = cb.createCriteriaUpdate(domainClass);
            Root<T> root = cu.from(domainClass);
            Converter.prepareUpdateQuery(updateParameters, cu);
            cu.where(cb.equal(root.get(pkAttribute), pkValue));
            entityManager.createQuery(cu).executeUpdate();
            entityManager.clear();
        } catch (Exception ex) {
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public List<T> getByParameters(List<GetParameter> parameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(domainClass);
        Root<T> root = cq.from(domainClass);
        Predicate predicate = Converter.preparePredicateWithSelectParameters(cb, cq, root, parameters);
        return getAllBy(cq, root, predicate);
    }

    protected List<T> getAllBy(CriteriaQuery<T> cq, Selection<? extends T> selection, Predicate predicate) {
        try {
            if (predicate != null) {
                cq.where(predicate);
            }
            cq.select(selection);
            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }
}