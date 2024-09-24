package com.senlainc.advertisementsystem.validatorapi;

import com.senlainc.advertisementsystem.model.order.purchase.Purchase;

public interface PurchaseValidator extends Validator<Purchase> {

    void checkPermission(Long userId, Long id);
}
