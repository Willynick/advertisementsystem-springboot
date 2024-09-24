package com.senlainc.advertisementsystem.validatorapi;

import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.advertisement.AdvertisementStatus;

public interface AdvertisementValidator extends Validator<Advertisement> {

    void checkEnoughMoney(double sendAmount, double amountForTop, double actualAmount);
    void checkAdvertisementHidden(AdvertisementStatus status);
    void checkPermission(Long userId, Long id);
}
