package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.user.profile.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRepository extends BaseRepository<Profile, Long> {

    @Query(value = "SELECT * FROM profiles p WHERE p.user_id = ?1", nativeQuery = true)
    Profile getByUserId(Long userId);
    @Query("SELECT p FROM Profile p JOIN p.user u WHERE u.username = ?1")
    Profile getByUserUsername(String username);
    @EntityGraph(attributePaths = { "address" })
    List<Profile> getByAddressCityId(Long cityId, Sort sort);
    @EntityGraph(attributePaths = { "address" })
    List<Profile> getByAddressCityCountryId(Long countryId, Sort sort);
}
