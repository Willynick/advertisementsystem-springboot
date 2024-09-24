package com.senlainc.advertisementsystem.config;

import com.senlainc.advertisementsystem.daospec.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = {"com.senlainc.advertisementsystem.dao", "com.senlainc.advertisementsystem.daospec"},
        repositoryBaseClass = BaseRepositoryImpl.class)
@EnableTransactionManagement
public class JpaConfig {
}
