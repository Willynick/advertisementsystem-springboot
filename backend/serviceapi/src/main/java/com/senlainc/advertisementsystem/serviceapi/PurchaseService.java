package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.dto.order.purchase.PurchaseDto;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchaseService {

    PurchaseDto add(Object principal, Long orderId, Long advertisementId);
    Boolean delete(Object principal, Long id);
    Boolean delete(Long id);
    PurchaseDto getById(Object principal, Long id);
    PurchaseDto getById(Long id);
    List<PurchaseDto> get(List<ViewSortParameter> parameters);
    List<PurchaseDto> getByOrder(Object principal, Long orderId, List<ViewSortParameter> parameters);
    List<PurchaseDto> getByOrder(Long orderId, List<ViewSortParameter> parameters);
    List<PurchaseDto> getByUser(Object principal, List<ViewSortParameter> parameters);
    List<PurchaseDto> getByUser(Long userId, List<ViewSortParameter> parameters);
    List<PurchaseDto> getByUserInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                      List<ViewSortParameter> parameters);
    List<PurchaseDto> getByUserInTime(Long userId, LocalDateTime from, LocalDateTime to, List<ViewSortParameter> parameters);
    List<PurchaseDto> getSalesByUser(Object principal, List<ViewSortParameter> parameters);
    List<PurchaseDto> getSalesByUser(Long userId, List<ViewSortParameter> parameters);
    List<PurchaseDto> getSalesByUserInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                           List<ViewSortParameter> parameters);
    List<PurchaseDto> getSalesByUserInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                           List<ViewSortParameter> parameters);
    List<PurchaseDto> getByAdvertisement(Object principal, Long advertisementId, List<ViewSortParameter> parameters);
    List<PurchaseDto> getByAdvertisement(Long advertisementId, List<ViewSortParameter> parameters);
    List<PurchaseDto> getByAdvertisementInTime(Object principal, Long advertisementId, LocalDateTime from, LocalDateTime to,
                                               List<ViewSortParameter> parameters);
    List<PurchaseDto> getByAdvertisementInTime(Long advertisementId, LocalDateTime from, LocalDateTime to,
                                               List<ViewSortParameter> parameters);
}
