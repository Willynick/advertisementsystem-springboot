package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileDto;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileUpdateDto;
import com.senlainc.advertisementsystem.serviceapi.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PutMapping
    public ProfileDto update(@AuthenticationPrincipal Authentication authentication,
                             @Valid @RequestBody ProfileUpdateDto profileDto) {
        return profileService.update(authentication.getPrincipal(), profileDto);
    }

    @PatchMapping
    public ProfileDto partiallyUpdate(@AuthenticationPrincipal Authentication authentication,
                                      @RequestBody List<ViewUpdateParameter> parameters) {
        return profileService.partiallyUpdate(authentication.getPrincipal(), parameters);
    }

    @DeleteMapping
    public ProfileDto delete(@AuthenticationPrincipal Authentication authentication) {
        return profileService.delete(authentication.getPrincipal());
    }

    @GetMapping("/{id}")
    public ProfileDto getById(@PathVariable("id") Long id) {
        return profileService.getById(id);
    }

    @GetMapping
    public List<ProfileDto> get(@RequestBody List<ViewSortParameter> parameters) {
        return profileService.get(parameters);
    }

    @GetMapping("/parameters")
    public List<ProfileDto> getByParameters(@RequestBody List<ViewGetParameter> parameters) {
        return profileService.getByParameters(parameters);
    }

    @GetMapping("/user/{id}")
    public ProfileDto getByUser(@PathVariable("id") Long userId) {
        return profileService.getByUser(userId);
    }

    @GetMapping("/user")
    public ProfileDto getByUsername(@RequestParam("username") String username) {
        return profileService.getByUsername(username);
    }

    @GetMapping("/city/{id}")
    public List<ProfileDto> getByCity(@PathVariable("id") Long cityId, @RequestBody List<ViewSortParameter> parameters) {
        return profileService.getByCity(cityId, parameters);
    }

    @GetMapping("/country/{id}")
    public List<ProfileDto> getByCountry(@PathVariable("id") Long countryId, @RequestBody List<ViewSortParameter> parameters) {
        return profileService.getByCountry(countryId, parameters);
    }

    @PutMapping("/money")
    public ProfileDto addMoney(@AuthenticationPrincipal Authentication authentication,
                              @Min(0) @RequestParam("amount") double amountOfMoney) {
        return profileService.addMoney(authentication.getPrincipal(), amountOfMoney);
    }
}
