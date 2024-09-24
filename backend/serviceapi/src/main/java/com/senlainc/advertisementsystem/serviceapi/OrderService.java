package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.order.OrderDto;
import com.senlainc.advertisementsystem.dto.order.OrderUpdateDto;
import com.senlainc.advertisementsystem.model.order.DeliveryType;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderDto add(Object principal, List<Long> advertisementsIds);
    OrderDto update(Object principal, Long id, OrderUpdateDto orderDto);
    OrderDto partiallyUpdate(Object principal, Long id, List<ViewUpdateParameter> parameters);
    OrderDto delete(Object principal, Long id);
    OrderDto delete(Long id);
    OrderDto getById(Object principal, Long id);
    OrderDto getById(Long id);
    List<OrderDto> get(List<ViewSortParameter> parameters);
    List<OrderDto> getByParameters(List<ViewGetParameter> parameters);
    List<OrderDto> getByUser(Object principal, List<ViewSortParameter> parameters);
    List<OrderDto> getByUser(Long userId, List<ViewSortParameter> parameters);
    List<OrderDto> getByUserInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                   List<ViewSortParameter> parameters);
    List<OrderDto> getByUserInTime(Long userId, LocalDateTime from, LocalDateTime to, List<ViewSortParameter> parameters);
    List<OrderDto> getCompleted(Object principal, List<ViewSortParameter> parameters);
    List<OrderDto> getCompleted(Long userId, List<ViewSortParameter> parameters);
    List<OrderDto> getCompletedAll(List<ViewSortParameter> parameters);
    List<OrderDto> getCompletedInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                      List<ViewSortParameter> parameters);
    List<OrderDto> getCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                      List<ViewSortParameter> parameters);
    List<OrderDto> getCompletedInTimeAll(LocalDateTime from, LocalDateTime to, List<ViewSortParameter> parameters);
    OrderDto setDeliveryType(Object principal, Long id, DeliveryType deliveryType);
    OrderDto order(Object principal, Long id);
    Double getTotalPrice(Object principal, Long id);
    Double getTotalPrice(Long id);
    Double getAmountOfMoneyEarnedInTime(Object principal, LocalDateTime from, LocalDateTime to);
    Double getAmountOfMoneyEarnedInTime(Long userId, LocalDateTime from, LocalDateTime to);
    Long getNumberCompletedInTime(Object principal, LocalDateTime from, LocalDateTime to);
    Long getNumberCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to);
}
