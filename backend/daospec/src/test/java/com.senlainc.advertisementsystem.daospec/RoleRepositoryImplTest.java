package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RoleRepositoryImplTest {

    private final RoleRepositoryImpl roleRepository;
    private Role role;

    @Autowired
    private TestEntityManager testEntityManager;

    public RoleRepositoryImplTest() {
        this.roleRepository = new RoleRepositoryImpl();
    }

    @BeforeEach
    public void setup() {
        this.roleRepository.setEntityManager(testEntityManager.getEntityManager());
        role = new Role("role", new ArrayList<>(), new ArrayList<>());
        roleRepository.create(role);
    }

    @Test
    public void save() {
        Role role =  new Role("role2", new ArrayList<>(), new ArrayList<>());
        roleRepository.create(role);

        assertNotNull(role.getId());
    }

    @Test
    public void getOne() {
        Role anotherRole = roleRepository.getByPK(role.getId());

        assertEquals(role, anotherRole);
    }

    @Test
    public void getAll() {
        List<Role> roles = roleRepository.getAll(new ArrayList<>());

        boolean isContains = roles.contains(role);
        assertNotNull(roles);
        assertTrue(isContains);
    }

}