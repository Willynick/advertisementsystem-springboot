package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.model.comment.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

//@Repository
public interface CommentRepository extends GenericDao<Comment, Long> /*extends BaseRepository<Comment, Long>*/ {

    List<Comment> getByProfileUserId(Long userId, List<SortParameter> sortParameters);
    List<Comment> getByAdvertisementId(Long advertisementId, List<SortParameter> sortParameters);
    List<Comment> getByProfileUserIdAndUploadDateBetween(Long userId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters);
    List<Comment> getByAdvertisementIdAndUploadDateBetween(Long advertisementId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters);
    //@Query(value = "select u.id from Comment c join c.profile p join p.user u where c.id = ?1")
    Long getProfileUserIdById(Long id);
}
