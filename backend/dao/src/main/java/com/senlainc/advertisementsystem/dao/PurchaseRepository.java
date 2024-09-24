package com.senlainc.advertisementsystem.dao;

import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseRepository extends BaseRepository<Purchase, Long> {

    List<Purchase> getByOrderProfileUserId(Long userId, Sort sort);
    List<Purchase> getByOrderProfileUserIdAndOrderDateBetween(Long userId, LocalDateTime from, LocalDateTime to, Sort sort);
    List<Purchase> getByAdvertisementId(Long advertisementId, Sort sort);
    List<Purchase> getByAdvertisementIdAndOrderDateBetween(Long advertisementId, LocalDateTime from, LocalDateTime to, Sort sort);
    List<Purchase> getByOrderId(Long orderId, Sort sort);
    List<Purchase> getByAdvertisementProfileUserId(Long userId, Sort sort);
    List<Purchase> getByAdvertisementProfileUserIdAndOrderDateBetween(Long userId, LocalDateTime from, LocalDateTime to, Sort sort);
    @Query(value = "select u.id from Purchase p join p.order o join o.profile p join p.user u where p.id = ?1")
    Long getOrderProfileUserIdById(Long id);
}
