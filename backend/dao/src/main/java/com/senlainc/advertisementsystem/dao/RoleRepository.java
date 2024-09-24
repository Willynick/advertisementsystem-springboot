package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.user.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends GenericDao<Role, Long> {// extends BaseRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    Role getByName(String name);
    List<Role> getByUsersId(Long userId);
}
