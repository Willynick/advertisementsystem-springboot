package com.senlainc.advertisementsystem.daospec.old;

import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.User_;
import com.senlainc.advertisementsystem.model.user.role.Role;
import com.senlainc.advertisementsystem.model.user.role.Role_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.ListJoin;

public class RoleSpecification {

    public static Specification<Role> getByUserId(Long userId) {
        return (role, cq, cb) -> {
            ListJoin<Role, User> roles = role.join(Role_.users);
            return cb.equal(roles.get(User_.id), userId);
        };
    }
}
