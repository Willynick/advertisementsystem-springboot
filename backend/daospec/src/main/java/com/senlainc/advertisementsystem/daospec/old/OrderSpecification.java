package com.senlainc.advertisementsystem.daospec.old;

import com.senlainc.advertisementsystem.model.order.Order;
import com.senlainc.advertisementsystem.model.order.OrderStatus;
import com.senlainc.advertisementsystem.model.order.OrderStatusModel;
import com.senlainc.advertisementsystem.model.order.OrderStatusModel_;
import com.senlainc.advertisementsystem.model.order.Order_;
import com.senlainc.advertisementsystem.model.user.User_;
import com.senlainc.advertisementsystem.model.user.profile.Profile_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class OrderSpecification {

    public static Specification<Order> getCompleted(Long userId) {
        return (order, cq, cb) -> {
            ListJoin<Order, OrderStatusModel> orders = order.join(Order_.orderStatuses);
            return cb.and(
                    cb.equal(order.get(Order_.profile).get(Profile_.user).get(User_.id), userId),
                    cb.equal(orders.get(OrderStatusModel_.status), OrderStatus.COMPLETED)
            );
        };
    }

    public static Specification<Order> getCompletedAll() {
        return (order, cq, cb) -> {
            ListJoin<Order, OrderStatusModel> orders = order.join(Order_.orderStatuses);
            return cb.equal(orders.get(OrderStatusModel_.status), OrderStatus.COMPLETED);
        };
    }

    public static Specification<Order> getCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to) {
        return (order, cq, cb) -> prepareCompletedOrdersInTime(userId, from, to, cb, order);
    }

    public static Specification<Order> getCompletedInTimeAll(LocalDateTime from, LocalDateTime to) {
        return (order, cq, cb) -> {
            ListJoin<Order, OrderStatusModel> orders = order.join(Order_.orderStatuses);
            return cb.and(
                    cb.equal(orders.get(OrderStatusModel_.status), OrderStatus.COMPLETED),
                    cb.greaterThan(order.get(Order_.date), from),
                    cb.lessThan(order.get(Order_.date), to)
            );
        };
    }

    static Predicate prepareCompletedOrdersInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                                  CriteriaBuilder cb, Root<Order> order) {
        ListJoin<Order, OrderStatusModel> orders = order.join(Order_.orderStatuses);

        return cb.and(
                cb.equal(order.get(Order_.profile).get(Profile_.user).get(User_.id), userId),
                cb.equal(orders.get(OrderStatusModel_.status), OrderStatus.COMPLETED),
                cb.greaterThan(order.get(Order_.date), from),
                cb.lessThan(order.get(Order_.date), to)
        );
    }
}
