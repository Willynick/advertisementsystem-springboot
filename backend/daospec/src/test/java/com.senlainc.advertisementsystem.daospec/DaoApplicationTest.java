package com.senlainc.advertisementsystem.daospec;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan("com.senlainc.advertisementsystem.model")
@EnableJpaRepositories(basePackages = {"com.senlainc.advertisementsystem.dao", "com.senlainc.advertisementsystem.daospec"},
        repositoryBaseClass = BaseRepositoryImpl.class)
@EnableTransactionManagement
public class DaoApplicationTest {

    @Test
    public void contextLoads() {
    }
}
