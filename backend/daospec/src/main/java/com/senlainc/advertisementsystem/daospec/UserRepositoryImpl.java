package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.UserRepository;
import com.senlainc.advertisementsystem.daospec.exception.DatabaseException;
import com.senlainc.advertisementsystem.model.address.Address_;
import com.senlainc.advertisementsystem.model.address.country.Country_;
import com.senlainc.advertisementsystem.model.address.country.city.City_;
import com.senlainc.advertisementsystem.model.user.Language;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.User_;
import com.senlainc.advertisementsystem.model.user.profile.Profile_;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
@Log4j2
public class UserRepositoryImpl extends JpaAbstractDao<User, Long> implements UserRepository {

    private final String GET_BY_PROFILE_ID = "select u from User u join u.profile p on p.id =:profileId";
    
    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public User getByUsername(String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        user.fetch(User_.roles, JoinType.INNER);
        Predicate predicate = cb.equal(user.get(User_.username), username);
        return getBy(cq, user, predicate, this.getClass(),"getByUsername");
    }

    @Override
    public User getByProfileId(Long profileId) {
        try {
            Query query = entityManager.createQuery(GET_BY_PROFILE_ID);
            query.setParameter("profileId", profileId);
            List<User> list = query.getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.iterator().next();
            }
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this.getClass(), "getByProfileId"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, this.getClass(), "getByProfileId"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public List<User> getByProfileAddressCityId(Long cityId, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        Predicate predicate = cb.equal(user.get(User_.profile).get(Profile_.address).get(Address_.city).get(City_.id), cityId);
        return getSortedBy(cb, cq, user, user, predicate, sortParameters, this.getClass(),"getByCityId");
    }

    @Override
    public List<User> getByProfileAddressCityCountryId(Long countryId, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        Predicate predicate = cb.equal(user.get(User_.profile).get(Profile_.address).get(Address_.city)
                .get(City_.country).get(Country_.id), countryId);
        return getSortedBy(cb, cq, user, user, predicate, sortParameters, this.getClass(),
                "getByCountryId");
    }

    @Override
    public List<User> getByLanguage(Language language, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        Predicate predicate = cb.equal(user.get(User_.language), language);
        return getSortedBy(cb, cq, user, user, predicate, sortParameters, this.getClass(),
                "getByLanguage");
    }
}
