package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.user.UserDto;
import com.senlainc.advertisementsystem.dto.user.UserUpdateDto;
import com.senlainc.advertisementsystem.model.user.Language;
import com.senlainc.advertisementsystem.serviceapi.UserService;
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
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserDto add(@Valid @RequestBody UserUpdateDto user) {
        return userService.add(user);
    }

    @PutMapping
    public UserDto update(@AuthenticationPrincipal Authentication authentication, @Valid @RequestBody UserUpdateDto user) {
        return userService.update(authentication.getPrincipal(), user);
    }

    @PatchMapping
    public UserDto partiallyUpdate(@AuthenticationPrincipal Authentication authentication,
                                   @RequestBody List<ViewUpdateParameter> parameters) {
        return userService.partiallyUpdate(authentication.getPrincipal(), parameters);
    }

    @DeleteMapping
    public UserDto delete(@AuthenticationPrincipal Authentication authentication) {
        return userService.delete(authentication.getPrincipal());
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable("id") Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserDto> get(@RequestBody List<ViewSortParameter> parameters) {
        return userService.get(parameters);
    }

    @GetMapping("/user")
    public UserDto getByUsername(@RequestParam("username") String username) {
        return userService.getByUsername(username);
    }

    @GetMapping("/profile/{id}")
    public UserDto getByProfile(@PathVariable("id") Long profileId) {
        return userService.getByProfile(profileId);
    }

    @GetMapping("/city/{id}")
    public List<UserDto> getByCity(@PathVariable("id") Long cityId, @RequestBody List<ViewSortParameter> parameters) {
        return userService.getByCity(cityId, parameters);
    }

    @GetMapping("/country/{id}")
    public List<UserDto> getByCountry(@PathVariable("id") Long countryId, @RequestBody List<ViewSortParameter> parameters) {
        return userService.getByCountry(countryId, parameters);
    }

    @GetMapping("/language")
    public List<UserDto> getByLanguage(@RequestParam("language") Language language,
                                       @RequestBody List<ViewSortParameter> parameters) {
        return userService.getByLanguage(language, parameters);
    }
}
