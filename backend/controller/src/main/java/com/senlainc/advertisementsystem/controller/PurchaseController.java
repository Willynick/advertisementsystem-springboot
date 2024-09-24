package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.dto.order.purchase.PurchaseDto;
import com.senlainc.advertisementsystem.serviceapi.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    public PurchaseDto add(@AuthenticationPrincipal Authentication authentication, @RequestParam("orderId") Long orderId,
                           @RequestParam("advertisementId") Long advertisementId) {
        return purchaseService.add(authentication.getPrincipal(), orderId, advertisementId);
    }

    @DeleteMapping("/{id}")
    public Boolean delete(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return purchaseService.delete(authentication.getPrincipal(), id);
    }

    @GetMapping("/{id}")
    public PurchaseDto getById(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long id) {
        return purchaseService.getById(authentication.getPrincipal(), id);
    }

    @GetMapping("/order/{id}")
    public List<PurchaseDto> getByOrder(@AuthenticationPrincipal Authentication authentication, @PathVariable("id") Long orderId,
                                        @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getByOrder(authentication.getPrincipal(), orderId, parameters);
    }

    @GetMapping("/user")
    public List<PurchaseDto> getByUser(@AuthenticationPrincipal Authentication authentication,
                                       @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getByUser(authentication.getPrincipal(), parameters);
    }

    @GetMapping("/user/time")
    public List<PurchaseDto> getByUserInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return purchaseService.getByUserInTime(authentication.getPrincipal(), from, to, parameters);
    }

    @GetMapping("/user/sales")
    public List<PurchaseDto> getSalesByUser(@AuthenticationPrincipal Authentication authentication,
                                            @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getSalesByUser(authentication.getPrincipal(), parameters);
    }

    @GetMapping("/user/sales/time")
    public List<PurchaseDto> getSalesByUserInTime(
            @AuthenticationPrincipal Authentication authentication,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return purchaseService.getSalesByUserInTime(authentication.getPrincipal(), from, to, parameters);
    }

    @GetMapping("/advertisement/{id}")
    public List<PurchaseDto> getByAdvertisement(@AuthenticationPrincipal Authentication authentication,
                                                @PathVariable("id") Long advertisementId,
                                                @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getByAdvertisement(authentication.getPrincipal(), advertisementId, parameters);
    }

    @GetMapping("/advertisement/{id}/time")
    public List<PurchaseDto> getByAdvertisementInTime(
            @AuthenticationPrincipal Authentication authentication,
            @PathVariable("id") Long advertisementId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return purchaseService.getByAdvertisementInTime(authentication.getPrincipal(), advertisementId, from, to, parameters);
    }
}
