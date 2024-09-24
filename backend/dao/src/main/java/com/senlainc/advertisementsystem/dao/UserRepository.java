package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.user.Language;
import com.senlainc.advertisementsystem.model.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends GenericDao<User, Long> {//extends BaseRepository<User, Long>, JpaSpecificationExecutor<User> {

    User getByUsername(String username);
    User getByProfileId(Long profileId);
    List<User> getByProfileAddressCityId(Long cityId, List<SortParameter> sortParameters);
    List<User> getByProfileAddressCityCountryId(Long countryId, List<SortParameter> sortParameters);
    List<User> getByLanguage(Language language, List<SortParameter> sortParameters);
}
