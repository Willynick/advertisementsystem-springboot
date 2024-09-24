package com.senlainc.advertisementsystem.validatorapi;

import com.senlainc.advertisementsystem.model.user.profile.raiting.Rating;

import java.util.List;

public interface RatingValidator extends Validator<Rating> {

    void checkRatingValue(double rating);
    void checkPermission(Long senderId, Long id);
    void checkUserAlreadyRated(List<Rating> ratings, Long senderId);
}
