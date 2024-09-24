package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.order.OrderDto;
import com.senlainc.advertisementsystem.dto.order.OrderUpdateDto;
import com.senlainc.advertisementsystem.model.order.DeliveryType;
import com.senlainc.advertisementsystem.serviceapi.OrderService;
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
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderDto add(@AuthenticationPrincipal Authentication authentication, @RequestBody List<Long> advertisementsIds) {
        return orderService.add(authentication.getPrincipal(), advertisementsIds);
    }

    @PutMapping("/{id}")
    public OrderDto update(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                           @RequestBody OrderUpdateDto orderDto) {
        return orderService.update(authentication.getPrincipal(), id, orderDto);
    }

    @PatchMapping("/{id}")
    public OrderDto partiallyUpdate(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                    @RequestBody List<ViewUpdateParameter> parameters) {
        return orderService.partiallyUpdate(authentication.getPrincipal(), id, parameters);
    }

    @DeleteMapping("/{id}")
    public OrderDto delete(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return orderService.delete(authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public OrderDto getById(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return orderService.getById(authentication.getPrincipal(), id);
    }

    @GetMapping("/user")
    public List<OrderDto> getByUser(@AuthenticationPrincipal Authentication authentication,
                                    @RequestBody List<ViewSortParameter> parameters) {
        return orderService.getByUser(authentication.getPrincipal(), parameters);
    }

    @GetMapping("/user/time")
    public List<OrderDto> getByUserInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return orderService.getByUserInTime(authentication.getPrincipal(), from, to, parameters);
    }

    @GetMapping("/user/completed")
    public List<OrderDto> getCompleted(@AuthenticationPrincipal Authentication authentication,
                                       @RequestBody List<ViewSortParameter> parameters) {
        return orderService.getCompleted(authentication.getPrincipal(), parameters);
    }

    @GetMapping("/user/completed/time")
    public List<OrderDto> getCompletedInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return orderService.getCompletedInTime(authentication.getPrincipal(), from, to, parameters);
    }

    @PutMapping("/{id}/delivery_type")
    public OrderDto setDeliveryType(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id,
                                    @RequestParam("deliveryType") DeliveryType deliveryType) {
        return orderService.setDeliveryType(authentication.getPrincipal(), id, deliveryType);
    }

    @PutMapping("/{id}/order")
    public OrderDto order(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return orderService.order(authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}/total_price")
    public Double getTotalPrice(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return orderService.getTotalPrice(authentication.getPrincipal(), id);
    }

    @GetMapping("/user/amount_of_money_earned/time")
    public Double getAmountOfMoneyEarnedInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to
    ) {
        return orderService.getAmountOfMoneyEarnedInTime(authentication.getPrincipal(), from, to);
    }

    @GetMapping("/user/number_completed/time")
    public Long getNumberCompletedInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to
    ) {
        return orderService.getNumberCompletedInTime(authentication.getPrincipal(), from, to);
    }
}
