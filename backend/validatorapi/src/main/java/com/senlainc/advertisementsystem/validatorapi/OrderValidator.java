package com.senlainc.advertisementsystem.validatorapi;

import com.senlainc.advertisementsystem.model.order.DeliveryType;
import com.senlainc.advertisementsystem.model.order.Order;
import com.senlainc.advertisementsystem.model.order.OrderStatusModel;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;

import java.util.List;

public interface OrderValidator extends Validator<Order> {

    void checkListAdvertisements(int size);
    void checkOrderCancelled(List<OrderStatusModel> statuses);
    void checkOrderCompleted(List<OrderStatusModel> statuses);
    void checkAdvertisementsHidden(List<Purchase> purchases);
    void checkDeliveryType(DeliveryType deliveryType);
    void checkPermission(Long userId, Long id);
}
