package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementDto;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementUpdateDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AdvertisementService {

    AdvertisementDto add(Object principal, AdvertisementUpdateDto advertisementDto);
    AdvertisementDto update(Object principal, Long id, AdvertisementUpdateDto advertisementDto);
    AdvertisementDto update(Long id, AdvertisementUpdateDto advertisementDto);
    AdvertisementDto partiallyUpdate(Object principal, Long id, List<ViewUpdateParameter> parameters);
    AdvertisementDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters);
    Boolean delete(Object principal, Long id);
    Boolean delete(Long id);
    AdvertisementDto getById(Long id);
    List<AdvertisementDto> get(List<ViewSortParameter> parameters);
    List<AdvertisementDto> getByParameters(List<ViewGetParameter> parameters);
    List<AdvertisementDto> getByUser(Long userId, List<ViewSortParameter> parameters);
    List<AdvertisementDto> getByCategory(Long categoryId, List<ViewSortParameter> parameters);
    List<AdvertisementDto> getInTheTop();
    List<AdvertisementDto> getByInterests(Object principal);
    List<AdvertisementDto> getByCity(Long cityId, List<ViewSortParameter> parameters);
    List<AdvertisementDto> getByCountry(Long countryId, List<ViewSortParameter> parameters);
    AdvertisementDto raiseInTheTop(Object principal, Long id, double amountOfMoney);
    AdvertisementDto raiseInTheTop(Long id);
    AdvertisementDto hide(Object principal, Long id);
    AdvertisementDto hide(Long id);
    AdvertisementDto restore(Object principal, Long id);
    AdvertisementDto restore(Long id);
}
