package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.message.MessageDto;
import com.senlainc.advertisementsystem.serviceapi.MessageService;
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
@RequestMapping(value = "/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public MessageDto add(@AuthenticationPrincipal Authentication authentication, @RequestParam("recipientId") Long recipientId,
                          @RequestBody String message) {
        return messageService.add(authentication.getPrincipal(), recipientId, message);
    }

    @PutMapping("/{id}")
    public MessageDto update(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                             @RequestBody String message) {
        return messageService.update(authentication.getPrincipal(), id, message);
    }

    @PatchMapping("/{id}")
    public MessageDto partiallyUpdate(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                      @RequestBody ViewUpdateParameter parameter) {
        return messageService.partiallyUpdate(authentication.getPrincipal(), id, parameter);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return messageService.delete(authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public MessageDto getById(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return messageService.getById(authentication.getPrincipal(), id);
    }

    @GetMapping("/user/sent")
    public List<MessageDto> getSent(@AuthenticationPrincipal Authentication authentication,
                                    @RequestBody List<ViewSortParameter> parameters) {
        return messageService.getSent(authentication.getPrincipal(), parameters);
    }

    @GetMapping("/user/received")
    public List<MessageDto> getReceived(@AuthenticationPrincipal Authentication authentication,
                                        @RequestBody List<ViewSortParameter> parameters) {
        return messageService.getReceived(authentication.getPrincipal(), parameters);
    }

    @GetMapping("/user/sent/time")
    public List<MessageDto> getSentInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return messageService.getSentInTime(authentication.getPrincipal(), from, to, parameters);
    }

    @GetMapping("/user/received/time")
    public List<MessageDto> getReceivedInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return messageService.getReceivedInTime(authentication.getPrincipal(), from, to, parameters);
    }

    @PutMapping("/readed/{id}")
    public MessageDto setReaded(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return messageService.setReaded(authentication.getPrincipal(), id);
    }

    @GetMapping("/hello")
    public String getSentInTime(
            @AuthenticationPrincipal Authentication authentication
    ) {
        return messageService.getHelloMessage(authentication.getPrincipal());
    }
}
