package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.GetParameter;
import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.backendutils.UpdateParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.GenericDao;
import com.senlainc.advertisementsystem.daospec.exception.DatabaseException;
import com.senlainc.advertisementsystem.model.abstractmodel.AbstractModel;
import lombok.extern.log4j.Log4j2;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;

@Log4j2
public abstract class JpaAbstractDao<T extends AbstractModel, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public JpaAbstractDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T object) {
        try {
            entityManager.persist(object);
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, type, "create"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public void update(T object) {
        try {
            entityManager.merge(object);
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, type, "update"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public void partiallyUpdate(SingularAttribute pkAttribute, PK pkValue, List<UpdateParameter> updateParameters) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaUpdate<T> cu = cb.createCriteriaUpdate(type);
            Root<T> root = cu.from(type);
            Converter.prepareUpdateQuery(updateParameters, cu);
            cu.where(cb.equal(root.get(pkAttribute), pkValue));
            entityManager.createQuery(cu).executeUpdate();
            entityManager.clear();
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, type, "partiallyUpdate"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public void delete(T object) {
        try {
            entityManager.remove(entityManager.contains(object) ? object : entityManager.merge(object));
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, type, "delete"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public T getByPK(PK id) {
        try {
            return entityManager.find(type, id);
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this, "getByPK"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, type, "getByPK"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public List<T> getAll(List<SortParameter> sortParameters) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<T> cq = cb.createQuery(type);
            Root<T> root = cq.from(type);
            Converter.prepareSortingQuery(sortParameters, root, root, cb, cq);

            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this, "getAll"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, type, "getAll"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public List<T> getByParameters(List<GetParameter> parameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(type);
        Root<T> root = cq.from(type);
        Predicate predicate = Converter.preparePredicateWithSelectParameters(cb, cq, root, parameters);
        return getAllBy(cq, root, predicate, this.getClass(), "getByParameters");
    }

    protected T getBy(CriteriaQuery<T> cq, Selection<? extends T> selection, Predicate predicate,
                      Class daoClass, String methodName) {
        try {
            cq.select(selection);
            if (predicate != null) {
                cq.where(predicate);
            }
            List<T> list =  entityManager.createQuery(cq).getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.iterator().next();
            }
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, daoClass, methodName));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, daoClass, methodName));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    protected List<T> getAllBy(CriteriaQuery<T> cq, Selection<? extends T> selection, Predicate predicate,
                               Class daoClass, String methodName) {
        try {
            if (predicate != null) {
                cq.where(predicate);
            }
            cq.select(selection);
            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, daoClass, methodName));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, daoClass, methodName));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    protected List<T> getSortedBy(CriteriaBuilder cb, CriteriaQuery<T> cq, Selection<? extends T> selection, Root<T> root,
                                  Predicate predicate, List<SortParameter> sortParameters, Class daoClass, String methodName) {
        try {
            Converter.prepareSortingQuery(sortParameters, selection, root, cb, cq);
            if (predicate != null) {
                cq.where(predicate);
            }
            return entityManager.createQuery(cq).getResultList();
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, daoClass, methodName));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, daoClass, methodName));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    protected Long getUserId(CriteriaBuilder cb, CriteriaQuery<Long> cq, Predicate predicate,
                             Selection<Long> selection, Class daoClass) {
        try {
            cq.select(selection);
            cq.where(predicate);
            List<Long> list = entityManager.createQuery(cq).getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.iterator().next();
            }
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, daoClass, "getUserId"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, daoClass, "getUserId"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }
}
