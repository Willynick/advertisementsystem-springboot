package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.user.profile.rating.RatingDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface RatingService {

    RatingDto add(Object principal, Long recipientId, int rating);
    RatingDto update(Object principal, Long id, int rating);
    RatingDto partiallyUpdate(Object principal, Long id, ViewUpdateParameter parameter);
    Boolean delete(Object principal, Long id);
    Boolean delete(Long id);
    RatingDto getById(Long id);
    List<RatingDto> get(ViewSortParameter parameter);
    List<RatingDto> getSent(Object principal, ViewSortParameter parameter);
    List<RatingDto> getReceived(Object principal, ViewSortParameter parameter);
}
