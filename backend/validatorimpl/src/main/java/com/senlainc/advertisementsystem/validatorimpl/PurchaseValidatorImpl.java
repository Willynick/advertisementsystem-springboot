package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.PurchaseRepository;
import com.senlainc.advertisementsystem.jwtsecutiry.exception.JwtAuthenticationException;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.validatorapi.PurchaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurchaseValidatorImpl extends AbstractValidator<Purchase> implements PurchaseValidator {

    private final PurchaseRepository purchaseRepository;

    @Autowired
    protected PurchaseValidatorImpl(PurchaseRepository purchaseRepository) {
        super("Purchase");
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public void checkPermission(Long userId, Long id) {
        if (!purchaseRepository.getOrderProfileUserIdById(id).equals(userId)) {
            throw new JwtAuthenticationException(Constants.ACCESS_DENIED);
        }
    }
}
