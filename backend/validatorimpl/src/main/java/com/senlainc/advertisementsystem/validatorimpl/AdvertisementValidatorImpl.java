package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.AdvertisementRepository;
import com.senlainc.advertisementsystem.jwtsecutiry.exception.JwtAuthenticationException;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.advertisement.AdvertisementStatus;
import com.senlainc.advertisementsystem.validatorapi.AdvertisementValidator;
import com.senlainc.advertisementsystem.validatorimpl.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementValidatorImpl extends AbstractValidator<Advertisement> implements AdvertisementValidator {

    @Autowired
    private AdvertisementRepository advertisementDao;

    @Autowired
    public AdvertisementValidatorImpl() {
        super("Advertisement");
    }

    @Override
    public void checkEnoughMoney(double sendAmount, double amountForTop, double actualAmount) {
        if (!(sendAmount >= amountForTop && actualAmount >= sendAmount)) {
            throw new ServiceException(Constants.NOT_ENOUGH_MONEY_MESSAGE);
        }
    }

    @Override
    public void checkAdvertisementHidden(AdvertisementStatus status) {
        if (status == AdvertisementStatus.HIDDEN) {
            throw new ServiceException(Constants.ADVERTISEMENT_HIDE_MESSAGE);
        }
    }

    @Override
    public void checkPermission(Long userId, Long id) {
        if (!advertisementDao.getProfileUserIdById(id).equals(userId)) {
            throw new JwtAuthenticationException(Constants.ACCESS_DENIED);
        }
    }
}
