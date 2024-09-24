package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementDto;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementUpdateDto;
import com.senlainc.advertisementsystem.serviceapi.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @PostMapping
    public AdvertisementDto add(@AuthenticationPrincipal Authentication authentication,
                                @Valid @RequestBody AdvertisementUpdateDto advertisementDto) {
        return advertisementService.add(authentication.getPrincipal(), advertisementDto);
    }

    @PutMapping("/{id}")
    public AdvertisementDto update(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                   @Valid @RequestBody AdvertisementUpdateDto advertisementDto) {
        return advertisementService.update(authentication.getPrincipal(), id, advertisementDto);
    }

    @PatchMapping("/{id}")
    public AdvertisementDto partiallyUpdate(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                            @RequestBody List<ViewUpdateParameter> parameters) {
        return advertisementService.partiallyUpdate(authentication.getPrincipal(), id, parameters);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return advertisementService.delete(authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public AdvertisementDto getById(@PathVariable("id") Long id) {
        return advertisementService.getById(id);
    }

    @GetMapping
    public List<AdvertisementDto> get(@RequestBody List<ViewSortParameter> parameters) {
        return advertisementService.get(parameters);
    }

    @GetMapping("/parameters")
    public List<AdvertisementDto> getByParameters(@RequestBody List<ViewGetParameter> parameters) {
        return advertisementService.getByParameters(parameters);
    }

    @GetMapping("/user/{id}")
    public List<AdvertisementDto> getByUser(@PathVariable("id") Long userId, @RequestBody List<ViewSortParameter> parameters) {
        return advertisementService.getByUser(userId, parameters);
    }

    @GetMapping("/category/{id}")
    public List<AdvertisementDto> getByCategory(@PathVariable("id") Long categoryId,
                                                @RequestBody List<ViewSortParameter> parameters) {
        return advertisementService.getByCategory(categoryId, parameters);
    }

    @GetMapping("/top")
    public List<AdvertisementDto> getInTheTop() {
        return advertisementService.getInTheTop();
    }

    @GetMapping("/user/interests")
    public List<AdvertisementDto> getByInterests(@AuthenticationPrincipal Authentication authentication) {
        return advertisementService.getByInterests(authentication.getPrincipal());
    }

    @GetMapping("/city/{id}")
    public List<AdvertisementDto> getByCity(@PathVariable("id") Long cityId, @RequestBody List<ViewSortParameter> parameters) {
        return advertisementService.getByCity(cityId, parameters);
    }

    @GetMapping("/country/{id}")
    public List<AdvertisementDto> getByCountry(@PathVariable("id") Long countryId,
                                               @RequestBody List<ViewSortParameter> parameters) {
        return advertisementService.getByCountry(countryId, parameters);
    }

    @PutMapping("/{id}/raise")
    public AdvertisementDto raiseInTheTop(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                          @RequestParam("amount") double amountOfMoney) {
        return advertisementService.raiseInTheTop(authentication.getPrincipal(), id, amountOfMoney);
    }

    @PutMapping("/{id}/hide")
    public AdvertisementDto hide(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return advertisementService.hide(authentication.getPrincipal(), id);
    }

    @PutMapping("/{id}/restore")
    public AdvertisementDto restore(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return advertisementService.restore(authentication.getPrincipal(), id);
    }
}
