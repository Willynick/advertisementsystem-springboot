package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.dto.address.country.CountryDto;
import com.senlainc.advertisementsystem.serviceapi.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{id}")
    public CountryDto getById(@PathVariable("id") Long id) {
        return countryService.getById(id);
    }

    @GetMapping
    public List<CountryDto> get(@RequestBody List<ViewSortParameter> parameters) {
        return countryService.get(parameters);
    }

    @GetMapping("/city/{id}")
    public CountryDto getByCity(@PathVariable("id") Long cityId) {
        return countryService.getByCity(cityId);
    }
}
