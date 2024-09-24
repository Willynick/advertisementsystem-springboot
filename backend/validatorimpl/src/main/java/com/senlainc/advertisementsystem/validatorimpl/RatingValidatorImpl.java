package com.senlainc.advertisementsystem.validatorimpl;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.RatingRepository;
import com.senlainc.advertisementsystem.jwtsecutiry.exception.JwtAuthenticationException;
import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating;
import com.senlainc.advertisementsystem.validatorapi.RatingValidator;
import com.senlainc.advertisementsystem.validatorimpl.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RatingValidatorImpl extends AbstractValidator<Rating> implements RatingValidator {

    private final RatingRepository ratingRepository;

    @Autowired
    protected RatingValidatorImpl(RatingRepository ratingRepository) {
        super("Rating");
        this.ratingRepository = ratingRepository;
    }

    @Override
    public void checkRatingValue(double rating) {
        if (!(rating >= 0 && rating <= 10)) {
            throw new ServiceException((Constants.RATING_VALUE_IS_NOT_POSSIBLE));
        }
    }

    @Override
    public void checkPermission(Long senderId, Long id) {
        if (!ratingRepository.getSenderUserIdById(id).equals(senderId)) {
            throw new JwtAuthenticationException(Constants.ACCESS_DENIED);
        }
    }

    @Override
    public void checkUserAlreadyRated(List<Rating> ratings, Long senderId) {
        if (ratings.stream().anyMatch(rating -> rating.getSender().getId().equals(senderId))) {
            throw new ServiceException(Constants.ALREADY_RATED_USER);
        }
    }
}
