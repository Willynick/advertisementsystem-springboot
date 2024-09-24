package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.address.country.city.CityView;
import com.senlainc.advertisementsystem.dto.address.country.city.CityDto;
import com.senlainc.advertisementsystem.dto.address.country.city.CityUpdateDto;

import java.util.List;

public interface CityService {

    CityDto add(CityUpdateDto cityDto);
    CityDto update(Long id, CityUpdateDto cityDto);
    CityDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters);
    Boolean delete(Long id);
    CityDto getById(Long id);
    List<CityDto> get(List<ViewSortParameter> parameters);
    List<CityView> getByCountry(Long countryId, List<ViewSortParameter> parameters);
}
