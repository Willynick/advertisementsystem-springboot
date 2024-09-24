package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.RoleRepository;
import com.senlainc.advertisementsystem.daospec.exception.DatabaseException;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.User_;
import com.senlainc.advertisementsystem.model.user.role.Role;
import com.senlainc.advertisementsystem.model.user.role.Role_;
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
public class RoleRepositoryImpl extends JpaAbstractDao<Role, Long> implements RoleRepository {

    private final String GET_ROLE_BY_USERNAME = "select * from roles r where r.name = ?";

    public RoleRepositoryImpl() {
        super(Role.class);
    }

    @Override
    public Role getByName(String name) {
        try {
            Query query = entityManager.createNativeQuery(GET_ROLE_BY_USERNAME);
            query.setParameter(1, name);
            List<Role> list = query.getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.iterator().next();
            }
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this.getClass(), "getByName"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, this.getClass(), "getByName"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public List<Role> getByUsersId(Long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> cq = cb.createQuery(Role.class);
        Root<Role> role = cq.from(Role.class);

        ListJoin<Role, User> roles = role.join(Role_.users);

        Predicate predicate = cb.equal(roles.get(User_.id), userId);
        return getAllBy(cq, role, predicate, this.getClass(),"getRolesByUserId");
    }
}
