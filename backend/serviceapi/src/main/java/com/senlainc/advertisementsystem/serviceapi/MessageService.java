package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.message.MessageDto;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageService {

    MessageDto add(Object principal, Long recipientId, String message);
    MessageDto update(Object principal, Long id, String message);
    MessageDto partiallyUpdate(Object principal, Long id, ViewUpdateParameter parameter);
    Boolean delete(Object principal, Long id);
    Boolean delete(Long id);
    MessageDto getById(Object principal, Long id);
    MessageDto getById(Long id);
    List<MessageDto> get(List<ViewSortParameter> parameters);
    List<MessageDto> getSent(Object principal, List<ViewSortParameter> parameters);
    List<MessageDto> getSent(Long userId, List<ViewSortParameter> parameters);
    List<MessageDto> getReceived(Object principal, List<ViewSortParameter> parameters);
    List<MessageDto> getReceived(Long userId, List<ViewSortParameter> parameters);
    List<MessageDto> getSentInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                   List<ViewSortParameter> sortParameters);
    List<MessageDto> getSentInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                   List<ViewSortParameter> sortParameters);
    List<MessageDto> getReceivedInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                       List<ViewSortParameter> sortParameters);
    List<MessageDto> getReceivedInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                       List<ViewSortParameter> sortParameters);
    MessageDto setReaded(Object principal, Long id);
    String getHelloMessage(Object principal);
}
