package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement_;
import com.senlainc.advertisementsystem.model.category.Category;
import com.senlainc.advertisementsystem.model.category.Category_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.ListJoin;

public class CategorySpecification {

    public static Specification<Category> getByAdvertisementId(Long advertisementId) {
        return (category, cq, cb) -> {
            ListJoin<Category, Advertisement> categories = category.join(Category_.advertisements);
            return cb.equal(categories.get(Advertisement_.id), advertisementId);
        };
    }
}
