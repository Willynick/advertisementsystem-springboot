package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.dto.address.country.city.CityView;
import com.senlainc.advertisementsystem.dto.address.country.city.CityDto;
import com.senlainc.advertisementsystem.serviceapi.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    public CityDto getById(@PathVariable("id") Long id) {
        return cityService.getById(id);
    }

    @GetMapping
    public List<CityDto> get(@RequestBody List<ViewSortParameter> parameters) {
        return cityService.get(parameters);
    }

    @GetMapping("/country/{id}")
    public List<CityView> getByCountry(@PathVariable("id") Long countryId, @RequestBody List<ViewSortParameter> parameters) {
        return cityService.getByCountry(countryId, parameters);
    }
}
