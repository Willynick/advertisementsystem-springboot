package com.senlainc.advertisementsystem.daospec;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.SortParameter;
import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.OrderRepository;
import com.senlainc.advertisementsystem.daospec.exception.DatabaseException;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement_;
import com.senlainc.advertisementsystem.model.order.Order;
import com.senlainc.advertisementsystem.model.order.*;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase_;
import com.senlainc.advertisementsystem.model.user.User_;
import com.senlainc.advertisementsystem.model.user.profile.Profile_;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Log4j2
public class OrderRepositoryImpl extends JpaAbstractDao<Order, Long> implements OrderRepository {

    private static final String GRAPH = "javax.persistence.fetchgraph";

    public OrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> getAll(List<SortParameter> sortParameters) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> cq = cb.createQuery(Order.class);
            Root<Order> root = cq.from(Order.class);
            Converter.prepareSortingQuery(sortParameters, root, root, cb, cq);
            EntityGraph graph = entityManager.getEntityGraph("order_entity_graph");

            return entityManager.createQuery(cq).setHint(GRAPH, graph).getResultList();
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this, "getAll"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, Order.class, "getAll"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public List<Order> getByProfileUserId(Long userId, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);
        Predicate predicate = cb.equal(order.get(Order_.profile).get(Profile_.user).get(User_.id), userId);
        return getSortedBy(cb, cq, order, order, predicate, sortParameters, this.getClass(),"getByUserId");
    }

    @Override
    public List<Order> getByProfileUserIdAndDateBetween(Long userId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);
        Predicate predicate = cb.and(
                cb.equal(order.get(Order_.profile).get(Profile_.user).get(User_.id), userId),
                cb.greaterThan(order.get(Order_.date), from),
                cb.lessThan(order.get(Order_.date), to)
        );
        return getSortedBy(cb, cq, order, order, predicate, sortParameters, this.getClass(),
                "getByUserIdInTime");
    }

    @Override
    public List<Order> getCompleted(Long userId, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);

        ListJoin<Order, OrderStatusModel> orders = order.join(Order_.orderStatuses);

        Predicate predicateOrder = cb.and(
                cb.equal(order.get(Order_.profile).get(Profile_.user).get(User_.id), userId),
                cb.equal(orders.get(OrderStatusModel_.status), OrderStatus.COMPLETED)
        );
        return getSortedBy(cb, cq, order, order, predicateOrder, sortParameters, this.getClass(),
                "getCompleted");
    }

    @Override
    public List<Order> getCompletedAll(List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);

        ListJoin<Order, OrderStatusModel> orders = order.join(Order_.orderStatuses);

        Predicate predicateOrder = cb.equal(orders.get(OrderStatusModel_.status), OrderStatus.COMPLETED);
        return getSortedBy(cb, cq, order, order, predicateOrder, sortParameters, this.getClass(),
                "getCompletedAll");
    }

    @Override
    public List<Order> getCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);

        Predicate predicateOrder = prepareCompletedOrdersInTime(userId, from, to, cb, order);

        return getSortedBy(cb, cq, order, order, predicateOrder, sortParameters, this.getClass(),
                "getCompletedInTime");
    }

    @Override
    public List<Order> getCompletedInTimeAll(LocalDateTime from, LocalDateTime to, List<SortParameter> sortParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> order = cq.from(Order.class);

        ListJoin<Order, OrderStatusModel> orders = order.join(Order_.orderStatuses);

        Predicate predicateOrder = cb.and(
                cb.equal(orders.get(OrderStatusModel_.status), OrderStatus.COMPLETED),
                cb.greaterThan(order.get(Order_.date), from),
                cb.lessThan(order.get(Order_.date), to)
        );

        return getSortedBy(cb, cq, order, order, predicateOrder, sortParameters,
                this.getClass(),"getCompletedInTimeAll");
    }

    @Override
    public Double getTotalPrice(Long id) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> cq = cb.createQuery(Double.class);
            Root<Order> order = cq.from(Order.class);

            ListJoin<Order, Purchase> orders = order.join(Order_.purchases);
            Predicate predicate = cb.equal(order.get(Order_.id), id);
            cq.where(predicate);

            Expression<Double> expression = orders.get(Purchase_.advertisement).get(Advertisement_.price);
            cq.select(cb.sum(expression));
            List<Double> list =  entityManager.createQuery(cq).getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.iterator().next();
            }
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this.getClass(), "getTotalPrice"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, this.getClass(), "getTotalPrice"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public Double getAmountOfMoneyEarnedInTime(Long userId, LocalDateTime from, LocalDateTime to) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Double> cq = cb.createQuery(Double.class);
            Root<Order> order = cq.from(Order.class);

            ListJoin<Order, Purchase> ordersWithPurchases = order.join(Order_.purchases);
            Predicate predicate = prepareCompletedOrdersInTime(userId, from, to, cb, order);
            cq.where(predicate);

            Expression<Double> expression = ordersWithPurchases.get(Purchase_.earnedMoney);
            cq.select(cb.sum(expression));
            List<Double> list =  entityManager.createQuery(cq).getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                Double value = list.iterator().next();
                return value == null ? 0.0 : value;
            }
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this.getClass(), "getAmountOfMoneyEarnedInTime"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, this.getClass(), "getAmountOfMoneyEarnedInTime"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public Long getNumberCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Order> order = cq.from(Order.class);

            Predicate predicate = prepareCompletedOrdersInTime(userId, from, to, cb, order);
            cq.where(predicate);

            cq.select(cb.count(order));
            List<Long> list =  entityManager.createQuery(cq).getResultList();
            if (list.isEmpty()) {
                return null;
            } else {
                return list.iterator().next();
            }
        } catch (NoResultException ex) {
            log.info(String.format(Constants.NO_RESULT_MESSAGE, this.getClass(), "getNumberCompletedInTime"));
            return null;
        } catch (Exception ex) {
            log.warn(String.format(Constants.ERROR_MESSAGE, this.getClass(), "getNumberCompletedInTime"));
            throw new DatabaseException(Constants.DATABASE_MESSAGE);
        }
    }

    @Override
    public Long getProfileUserIdById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Order> order = cq.from(Order.class);
        Predicate predicate = cb.equal(order.get(Order_.id), id);
        Selection<Long> selection = order.get(Order_.profile).get(Profile_.user).get(User_.id);
        return getUserId(cb, cq, predicate, selection, this.getClass());
    }

    private Predicate prepareCompletedOrdersInTime(Long userId, LocalDateTime from, LocalDateTime to,
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
