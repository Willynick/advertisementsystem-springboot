package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.model.address.country.Country;
import com.senlainc.advertisementsystem.model.address.country.Country_;
import com.senlainc.advertisementsystem.model.address.country.city.City;
import com.senlainc.advertisementsystem.model.address.country.city.City_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.ListJoin;

public class CountrySpecification {

    public static Specification<Country> getByCityId(Long cityId) {
        return (country, cq, cb) -> {
            ListJoin<Country, City> countries = country.join(Country_.cities);
            return cb.equal(countries.get(City_.id), cityId);
        };
    }
}
