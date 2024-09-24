package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.comment.CommentDto;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {

    CommentDto add(Object principal, Long advertisementId, String text);
    CommentDto update(Object principal, Long id, String text);
    CommentDto update(Long id, String text);
    CommentDto partiallyUpdate(Object principal, Long id, ViewUpdateParameter parameter);
    CommentDto partiallyUpdate(Long id, ViewUpdateParameter parameter);
    Boolean delete(Object principal, Long id);
    Boolean delete(Long id);
    CommentDto getById(Long id);
    List<CommentDto> get(List<ViewSortParameter> parameters);
    List<CommentDto> getByUser(Long userId, List<ViewSortParameter> parameters);
    List<CommentDto> getByAdvertisement(Long advertisementId, List<ViewSortParameter> parameters);
    List<CommentDto> getByUserInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                     List<ViewSortParameter> sortParameters);
    List<CommentDto> getByAdvertisementInTime(Long advertisementId, LocalDateTime from, LocalDateTime to,
                                              List<ViewSortParameter> sortParameters);
}
