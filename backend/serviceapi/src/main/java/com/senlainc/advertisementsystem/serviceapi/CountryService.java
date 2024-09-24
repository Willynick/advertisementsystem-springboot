package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.address.country.CountryDto;
import com.senlainc.advertisementsystem.dto.address.country.CountryUpdateDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CountryService {

    CountryDto add(CountryUpdateDto countryDto);
    CountryDto update(Long id, CountryUpdateDto countryDto);
    CountryDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters);
    Boolean delete(Long id);
    CountryDto getById(Long id);
    List<CountryDto> get(List<ViewSortParameter> parameters);
    CountryDto getByCity(Long cityId);
}
