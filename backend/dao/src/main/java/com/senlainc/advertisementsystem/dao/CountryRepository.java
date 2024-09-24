package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.address.country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends BaseRepository<Country, Long>, JpaSpecificationExecutor<Country> {

    //Country getByCitiesId(Long cityId);
}
