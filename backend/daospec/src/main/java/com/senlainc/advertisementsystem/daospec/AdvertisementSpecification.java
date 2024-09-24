package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.model.address.Address;
import com.senlainc.advertisementsystem.model.address.Address_;
import com.senlainc.advertisementsystem.model.address.country.Country_;
import com.senlainc.advertisementsystem.model.address.country.city.City_;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.advertisement.AdvertisementStatus;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement_;
import com.senlainc.advertisementsystem.model.user.profile.Profile_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.ListJoin;

public class AdvertisementSpecification {

    public static Specification<Advertisement> getInTheTop() {

        return (advertisement, cq, cb) -> {
            cq.orderBy(cb.desc(advertisement.get(Advertisement_.profile).get(Profile_.averageRating)));
            return cb.notEqual(advertisement.get(Advertisement_.advertisementStatus), AdvertisementStatus.HIDDEN);
        };
    }

    public static Specification<Advertisement> getByCountryId(Long countryId) {
        return (advertisement, cq, cb) -> {
            ListJoin<Advertisement, Address> advertisements = advertisement.join(Advertisement_.addresses);
            return cb.equal(advertisements.get(Address_.city).get(City_.country).get(Country_.id), countryId);
        };
    }
}
