package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.comment.CommentDto;
import com.senlainc.advertisementsystem.serviceapi.CommentService;
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

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public CommentDto add(@AuthenticationPrincipal Authentication authentication,
                          @RequestParam("advertisementId") Long advertisementId,
                          @RequestBody String text) {
        return commentService.add(authentication.getPrincipal(), advertisementId, text);
    }

    @PutMapping("/{id}")
    public CommentDto update(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                            @RequestBody String text) {
        return commentService.update(authentication.getPrincipal(), id, text);
    }

    @PatchMapping("/{id}")
    public CommentDto partiallyUpdate(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                      @RequestBody ViewUpdateParameter parameter) {
        return commentService.partiallyUpdate(authentication.getPrincipal(), id, parameter);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return commentService.delete(authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public CommentDto getById(@PathVariable("id") Long id)   {
        return commentService.getById(id);
    }

    @GetMapping
    public List<CommentDto> get(@RequestBody List<ViewSortParameter> parameters) {
        return commentService.get(parameters);
    }

    @GetMapping("/user/{id}")
    public List<CommentDto> getByUser(@PathVariable("id") Long userId, @RequestBody List<ViewSortParameter> parameters) {
        return commentService.getByUser(userId, parameters);
    }

    @GetMapping("/advertisement/{id}")
    public List<CommentDto> getByAdvertisement(@PathVariable("id") Long advertisementId,
                                               @RequestBody List<ViewSortParameter> parameters) {
        return commentService.getByAdvertisement(advertisementId, parameters);
    }

    @GetMapping("/user/{id}/time")
    public List<CommentDto> getByUserInTime(
            @PathVariable("id") Long userId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return commentService.getByUserInTime(userId, from, to, parameters);
    }

    @GetMapping("/advertisement/{id}/time")
    public List<CommentDto> getByAdvertisementInTime(
            @PathVariable("id") Long advertisementId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return commentService.getByAdvertisementInTime(advertisementId, from, to, parameters);
    }
}
