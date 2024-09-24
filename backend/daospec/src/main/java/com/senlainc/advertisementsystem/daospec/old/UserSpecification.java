package com.senlainc.advertisementsystem.daospec.old;

import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.User_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;

public class UserSpecification {

    public static Specification<User> getByUsername(String username) {
        return (user, cq, cb) -> {
            user.fetch(User_.roles, JoinType.INNER);
            return cb.equal(user.get(User_.username), username);
        };
    }
}
