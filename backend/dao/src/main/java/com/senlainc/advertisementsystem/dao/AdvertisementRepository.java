package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertisementRepository extends BaseRepository<Advertisement, Long>, JpaSpecificationExecutor<Advertisement> {

    List<Advertisement> getByProfileUserId(Long userId, Sort sort);
    List<Advertisement> getByCategoryId(Long categoryId, Sort sort);
    //List<Advertisement> getInTheTop();
    List<Advertisement> getByAddressesCityId(Long cityId, Sort sort);
    //List<Advertisement> getByCountryId(Long countryId, Sort sort);
    @Query(value = "select u.id from Advertisement a join a.profile p join p.user u where a.id = ?1")
    Long getProfileUserIdById(Long id);
}
