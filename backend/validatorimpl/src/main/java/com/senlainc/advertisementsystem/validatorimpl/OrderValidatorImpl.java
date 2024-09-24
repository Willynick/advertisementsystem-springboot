package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.OrderRepository;
import com.senlainc.advertisementsystem.jwtsecutiry.exception.JwtAuthenticationException;
import com.senlainc.advertisementsystem.model.advertisement.AdvertisementStatus;
import com.senlainc.advertisementsystem.model.order.DeliveryType;
import com.senlainc.advertisementsystem.model.order.Order;
import com.senlainc.advertisementsystem.model.order.OrderStatus;
import com.senlainc.advertisementsystem.model.order.OrderStatusModel;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.validatorapi.OrderValidator;
import com.senlainc.advertisementsystem.validatorimpl.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderValidatorImpl extends AbstractValidator<Order> implements OrderValidator {

    private final OrderRepository orderRepository;

    @Autowired
    protected OrderValidatorImpl(OrderRepository orderRepository) {
        super("Order");
        this.orderRepository = orderRepository;
    }

    @Override
    public void checkListAdvertisements(int size) {
        if (size == 0) {
            throw new ServiceException(Constants.EMPTY_ADVERTISEMENTS_LIST_MESSAGE);
        }
    }

    @Override
    public void checkOrderCancelled(List<OrderStatusModel> statuses) {
        if (statuses.stream().anyMatch(status -> status.getStatus().equals(OrderStatus.CANCELED))) {
            throw new ServiceException(Constants.ORDER_ALREADY_CANCELLED);
        }
    }

    @Override
    public void checkOrderCompleted(List<OrderStatusModel> statuses) {
        if (statuses.stream().anyMatch(status -> status.getStatus().equals(OrderStatus.COMPLETED))) {
            throw new ServiceException(Constants.ORDER_ALREADY_COMPLETED);
        }
    }

    @Override
    public void checkAdvertisementsHidden(List<Purchase> purchases) {
        if (purchases.stream().anyMatch(purchase ->
                purchase.getAdvertisement().getAdvertisementStatus().equals(AdvertisementStatus.HIDDEN))) {
            throw new ServiceException(Constants.ADVERTISEMENT_IS_NOT_AVAILABLE);
        }
    }

    @Override
    public void checkDeliveryType(DeliveryType deliveryType) {
        if (deliveryType == null) {
            throw new ServiceException(Constants.CHOOSE_DELIVERY_TYPE);
        }
    }

    @Override
    public void checkPermission(Long userId, Long id) {
        if (!orderRepository.getProfileUserIdById(id).equals(userId)) {
            throw new JwtAuthenticationException(Constants.ACCESS_DENIED);
        }
    }
}
