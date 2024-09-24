package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.category.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @EntityGraph(value = "category_entity_graph")
    Category getParentCategoryById(Long id);
    List<Category> getByParentCategoryId(Long id, Sort sort);
    //Category getByAdvertisementId(Long advertisementId);
}
