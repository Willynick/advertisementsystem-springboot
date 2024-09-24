package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.dto.address.country.city.CityView;
import com.senlainc.advertisementsystem.model.address.country.city.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends BaseRepository<City, Long> {

    @Query(value = "SELECT * FROM cities c WHERE c.country_id = ?1", nativeQuery = true)
    List<CityView> getByCountryId(@Param("countryId") Long countryId);
}
