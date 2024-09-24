package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends BaseRepository<Rating, Long> {

    List<Rating> getBySenderUserId(Long senderId, Sort sort);
    List<Rating> getByRecipientUserId(Long recipientId, Sort sort);
    @Query(value = "select u.id from Rating r join r.sender s join s.user u where r.id = ?1")
    Long getSenderUserIdById(Long id);
    @Query(value = "select u.id from Rating r join r.recipient rec join rec.user u where r.id = ?1")
    Long getRecipientUserIdById(Long id);
}
