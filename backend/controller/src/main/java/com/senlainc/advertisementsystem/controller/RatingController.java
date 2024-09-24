package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.user.profile.rating.RatingDto;
import com.senlainc.advertisementsystem.serviceapi.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public RatingDto add(@AuthenticationPrincipal Authentication authentication, @RequestParam("recipientId") Long recipientId,
                         @Max(10) @Min(1) @RequestParam("rating") Integer rating) {
        return ratingService.add(authentication.getPrincipal(), recipientId, rating);
    }

    @PutMapping("/{id}")
    public RatingDto update(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                            @Max(10) @Min(1) @RequestParam("rating") Integer rating) {
        return ratingService.update(authentication.getPrincipal(), id, rating);
    }

    @PatchMapping("/{id}")
    public RatingDto partiallyUpdate(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                     @RequestBody ViewUpdateParameter parameter) {
        return ratingService.partiallyUpdate(authentication.getPrincipal(), id, parameter);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return ratingService.delete(authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public RatingDto getById(@PathVariable("id") Long id) {
        return ratingService.getById(id);
    }

    @GetMapping
    public List<RatingDto> get(@RequestBody ViewSortParameter parameter) {
        return ratingService.get(parameter);
    }

    @GetMapping("/user/sent")
    public List<RatingDto> getSent(@AuthenticationPrincipal Authentication authentication,
                                   @RequestBody ViewSortParameter parameter) {
        return ratingService.getSent(authentication.getPrincipal(), parameter);
    }

    @GetMapping("/user/received")
    public List<RatingDto> getReceived(@AuthenticationPrincipal Authentication authentication,
                                       @RequestBody ViewSortParameter parameter) {
        return ratingService.getReceived(authentication.getPrincipal(), parameter);
    }
}
