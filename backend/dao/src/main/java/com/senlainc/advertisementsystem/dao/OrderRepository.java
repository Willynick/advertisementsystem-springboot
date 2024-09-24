package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.model.comment.Comment;
import com.senlainc.advertisementsystem.model.order.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends GenericDao<Order, Long> {//extends BaseRepository<Order, Long>, OrderRepositoryCustom, JpaSpecificationExecutor<Order> {

    List<Order> getByProfileUserId(Long userId, List<SortParameter> sortParameters);
    List<Order> getByProfileUserIdAndDateBetween(Long userId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters);
    List<Order> getCompleted(Long userId, List<SortParameter> sortParameters);
    List<Order> getCompletedAll(List<SortParameter> sortParameters);
    List<Order> getCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters);
    List<Order> getCompletedInTimeAll(LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters);
    Double getTotalPrice(Long id);
    Double getAmountOfMoneyEarnedInTime(Long userId, LocalDateTime from, LocalDateTime to);
    Long getNumberCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to);
    @Query(value = "select u.id from Order o join o.profile p join p.user u where o.id = ?1")
    Long getProfileUserIdById(Long id);
}
