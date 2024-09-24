package com.senlainc.advertisementsystem.controller;

import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.address.country.CountryDto;
import com.senlainc.advertisementsystem.dto.address.country.CountryUpdateDto;
import com.senlainc.advertisementsystem.dto.address.country.city.CityDto;
import com.senlainc.advertisementsystem.dto.address.country.city.CityUpdateDto;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementDto;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementUpdateDto;
import com.senlainc.advertisementsystem.dto.category.CategoryDto;
import com.senlainc.advertisementsystem.dto.category.CategoryUpdateDto;
import com.senlainc.advertisementsystem.dto.comment.CommentDto;
import com.senlainc.advertisementsystem.dto.message.MessageDto;
import com.senlainc.advertisementsystem.dto.order.OrderDto;
import com.senlainc.advertisementsystem.dto.order.purchase.PurchaseDto;
import com.senlainc.advertisementsystem.dto.user.UserDto;
import com.senlainc.advertisementsystem.dto.user.UserUpdateDto;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileDto;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileUpdateDto;
import com.senlainc.advertisementsystem.dto.user.role.RoleDto;
import com.senlainc.advertisementsystem.serviceapi.AdvertisementService;
import com.senlainc.advertisementsystem.serviceapi.CategoryService;
import com.senlainc.advertisementsystem.serviceapi.CityService;
import com.senlainc.advertisementsystem.serviceapi.CommentService;
import com.senlainc.advertisementsystem.serviceapi.CountryService;
import com.senlainc.advertisementsystem.serviceapi.MessageService;
import com.senlainc.advertisementsystem.serviceapi.OrderService;
import com.senlainc.advertisementsystem.serviceapi.ProfileService;
import com.senlainc.advertisementsystem.serviceapi.PurchaseService;
import com.senlainc.advertisementsystem.serviceapi.RatingService;
import com.senlainc.advertisementsystem.serviceapi.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdvertisementService advertisementService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final CommentService commentService;
    private final CountryService countryService;
    private final MessageService messageService;
    private final OrderService orderService;
    private final ProfileService profileService;
    private final PurchaseService purchaseService;
    private final RatingService ratingService;
    private final UserService userService;

    @Autowired
    public AdminController(AdvertisementService advertisementService, CategoryService categoryService,
                           CityService cityService, CommentService commentService, CountryService countryService,
                           MessageService messageService, OrderService orderService, ProfileService profileService,
                           PurchaseService purchaseService, RatingService ratingService, UserService userService) {
        this.advertisementService = advertisementService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.commentService = commentService;
        this.countryService = countryService;
        this.messageService = messageService;
        this.orderService = orderService;
        this.profileService = profileService;
        this.purchaseService = purchaseService;
        this.ratingService = ratingService;
        this.userService = userService;
    }
    
    @PreAuthorize("hasAuthority('OP_UPDATE_ADVERTISEMENT')")
    @PutMapping("/advertisements/{id}")
    public AdvertisementDto updateAdvertisement(@PathVariable("id") Long id,
                                                @Valid @RequestBody AdvertisementUpdateDto advertisementDto) {
        return advertisementService.update(id, advertisementDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_ADVERTISEMENT')")
    @PatchMapping("/advertisements/{id}")
    public AdvertisementDto partiallyUpdateAdvertisement(@PathVariable("id") Long id, @RequestBody List<ViewUpdateParameter> parameters) {
        return advertisementService.partiallyUpdate(id, parameters);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_ADVERTISEMENT')")
    @DeleteMapping("/advertisements/{id}")
    public Boolean deleteAdvertisement(@PathVariable("id") Long id) {
        return advertisementService.delete(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERATOR', 'ROLE_ADMIN')")
    @PutMapping("/advertisements/{id}/raise")
    public AdvertisementDto raiseInTheTopAdvertisement(@PathVariable("id") Long id) {
        return advertisementService.raiseInTheTop(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_ADVERTISEMENT')")
    @PutMapping("/advertisements/{id}/hide")
    public AdvertisementDto hideAdvertisement(@PathVariable("id") Long id) {
        return advertisementService.hide(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_ADVERTISEMENT')")
    @PutMapping("/advertisements/{id}/restore")
    public AdvertisementDto restoreAdvertisement(@PathVariable("id") Long id) {
        return advertisementService.restore(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CATEGORY')")
    @PostMapping("/categories")
    public CategoryDto addCategory(@Valid @RequestBody CategoryUpdateDto categoryDto) {
        return categoryService.add(categoryDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CATEGORY')")
    @PutMapping("/categories/{id}")
    public CategoryDto updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryUpdateDto categoryDto) {
        return categoryService.update(id, categoryDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CATEGORY')")
    @PatchMapping("/categories/{id}")
    public CategoryDto partiallyUpdateCategory(@PathVariable("id") Long id, @RequestBody List<ViewUpdateParameter> parameters) {
        return categoryService.partiallyUpdate(id, parameters);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CATEGORY')")
    @DeleteMapping("/categories/{id}")
    public Boolean deleteCategory(@PathVariable("id") Long id) {
        return categoryService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CITY')")
    @PostMapping("/cities")
    public CityDto addCity(@Valid @RequestBody CityUpdateDto cityDto) {
        return cityService.add(cityDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CITY')")
    @PutMapping("/cities/{id}")
    public CityDto updateCity(@PathVariable("id") Long id, @Valid @RequestBody CityUpdateDto cityDto) {
        return cityService.update(id, cityDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CITY')")
    @PatchMapping("/cities/{id}")
    public CityDto partiallyUpdateCity(@PathVariable("id") Long id, @RequestBody List<ViewUpdateParameter> parameters) {
        return cityService.partiallyUpdate(id, parameters);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_CITY')")
    @DeleteMapping("/cities/{id}")
    public Boolean deleteCity(@PathVariable("id") Long id) {
        return cityService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_COMMENT')")
    @PutMapping("/comments/{id}")
    public CommentDto updateComment(@PathVariable("id") Long id, @RequestBody String text) {
        return commentService.update(id, text);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_COMMENT')")
    @PatchMapping("/comments/{id}")
    public CommentDto partiallyUpdateComment(@PathVariable("id") Long id, @RequestBody ViewUpdateParameter parameter) {
        return commentService.partiallyUpdate(id, parameter);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_COMMENT')")
    @DeleteMapping("/comments/{id}")
    public Boolean deleteComment(@PathVariable("id") Long id) {
        return commentService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_COUNTRY')")
    @PostMapping("/countries")
    public CountryDto addCountry(@Valid @RequestBody CountryUpdateDto countryDto) {
        return countryService.add(countryDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_COUNTRY')")
    @PutMapping("/countries/{id}")
    public CountryDto updateCountry(@PathVariable("id") Long id, @Valid @RequestBody CountryUpdateDto countryDto) {
        return countryService.update(id, countryDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_COUNTRY')")
    @PatchMapping("/countries/{id}")
    public CountryDto partiallyUpdateCountry(@PathVariable("id") Long id, @RequestBody List<ViewUpdateParameter> parameters) {
        return countryService.partiallyUpdate(id, parameters);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_COUNTRY')")
    @DeleteMapping("/countries/{id}")
    public Boolean deleteCountry(@PathVariable("id") Long id) {
        return countryService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_MESSAGE')")
    @DeleteMapping("/messages/{id}")
    public Boolean deleteMessage(@PathVariable("id") Long id) {
        return messageService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_MESSAGE')")
    @GetMapping("/messages/{id}")
    public MessageDto getByIdMessage(@PathVariable("id") Long id) {
        return messageService.getById(id);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_MESSAGE')")
    @GetMapping("/messages")
    public List<MessageDto> getMessages(@RequestBody List<ViewSortParameter> parameters) {
        return messageService.get(parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_MESSAGE')")
    @GetMapping("/messages/user/{id}/sent")
    public List<MessageDto> getSentMessages(@PathVariable("id") Long senderId, @RequestBody List<ViewSortParameter> parameters) {
        return messageService.getSent(senderId, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_MESSAGE')")
    @GetMapping("/messages/user/{id}/received")
    public List<MessageDto> getReceivedMessages(@PathVariable("id") Long recipientId, @RequestBody List<ViewSortParameter> parameters) {
        return messageService.getReceived(recipientId, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_MESSAGE')")
    @GetMapping("/messages/user/{id}/sent/time")
    public List<MessageDto> getSentInTimeMessages(
            @PathVariable("id") Long senderId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return messageService.getSentInTime(senderId, from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_MESSAGE')")
    @GetMapping("/messages/user/{id}/received/time")
    public List<MessageDto> getReceivedInTimeMessages(
            @PathVariable("id") Long recipientId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return messageService.getReceivedInTime(recipientId, from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_DELETE_ORDER')")
    @DeleteMapping("/orders/{id}")
    public OrderDto deleteOrder(@PathVariable("id") Long id) {
        return orderService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/{id}")
    public OrderDto getByIdOrder(@PathVariable("id") Long id) {
        return orderService.getById(id);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders")
    public List<OrderDto> getOrders(@RequestBody List<ViewSortParameter> parameters) {
        return orderService.get(parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/parameters")
    public List<OrderDto> getByParametersOrders(@RequestBody List<ViewGetParameter> parameters) {
        return orderService.getByParameters(parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/user/{id}")
    public List<OrderDto> getByUserOrders(@PathVariable("id") Long id, @RequestBody List<ViewSortParameter> parameters) {
        return orderService.getByUser(id, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/user/{id}/time")
    public List<OrderDto> getByUserInTimeOrders(
            @PathVariable("id") Long id,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return orderService.getByUserInTime(id, from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/user/{id}/completed")
    public List<OrderDto> getCompletedOrders(@PathVariable("id") Long id, @RequestBody List<ViewSortParameter> parameters) {
        return orderService.getCompleted(id, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/completed")
    public List<OrderDto> getCompletedAllOrders(@RequestBody List<ViewSortParameter> parameters) {
        return orderService.getCompletedAll(parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/user/{id}/completed/time")
    public List<OrderDto> getCompletedInTimeOrders(
            @PathVariable("id") Long id,
            @RequestParam("from") LocalDateTime from,
            @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return orderService.getCompletedInTime(id, from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/completed/time")
    public List<OrderDto> getCompletedInTimeAllOrders(
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return orderService.getCompletedInTimeAll(from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/{id}/total_price")
    public Double getTotalPriceOrder(@PathVariable("id") Long id) {
        return orderService.getTotalPrice(id);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/user/{id}/amount_of_money_earned/time")
    public Double getAmountOfMoneyEarnedInTimeOrders(
            @PathVariable("id") Long id,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to
    ) {
        return orderService.getAmountOfMoneyEarnedInTime(id, from, to);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_ORDER')")
    @GetMapping("/orders/user/{id}/number_completed/time")
    public Long getNumberCompletedInTimeOrders(
            @PathVariable("id") Long id,
            @RequestParam("from") LocalDateTime from,
            @RequestParam("to") LocalDateTime to
    ) {
        return orderService.getNumberCompletedInTime(id, from, to);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_PROFILE')")
    @PutMapping("/profiles/user/{id}")
    public ProfileDto updateProfile(@PathVariable("id") Long userId, @RequestBody ProfileUpdateDto profileDto) {
        return profileService.update(userId, profileDto);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_PROFILE')")
    @PatchMapping("/profiles/user/{id}")
    public ProfileDto partiallyUpdateProfile(@PathVariable("id") Long userId, @RequestBody List<ViewUpdateParameter> parameters) {
        return profileService.partiallyUpdate(userId, parameters);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_PROFILE')")
    @DeleteMapping("/profiles/user/{id}")
    public ProfileDto deleteProfile(@PathVariable("id") Long userId) {
        return profileService.delete(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/profiles/user/{id}/money")
    public ProfileDto addMoneyProfile(@PathVariable("id") Long id, @Min(1) @RequestParam("amount") double amountOfMoney) {
        return profileService.addMoney(id, amountOfMoney);
    }

    @PreAuthorize("hasAuthority('OP_DELETE_PURCHASE')")
    @DeleteMapping("/purchases/{id}")
    public Boolean deletePurchase(@PathVariable("id") Long id) {
        return purchaseService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/{id}")
    public PurchaseDto getByIdPurchase(@PathVariable("id") Long id) {
        return purchaseService.getById(id);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases")
    public List<PurchaseDto> getPurchases(@RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.get(parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/order/{id}")
    public List<PurchaseDto> getByOrderPurchases(@PathVariable("id") Long orderId,
                                        @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getByOrder(orderId, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/user/{id}")
    public List<PurchaseDto> getByUserPurchases(@PathVariable("id") Long userId, @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getByUser(userId, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/user/{id}/time")
    public List<PurchaseDto> getByUserInTimePurchases(
            @PathVariable("id") Long userId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return purchaseService.getByUserInTime(userId, from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/user/{id}/sales")
    public List<PurchaseDto> getSalesByUserPurchases(@PathVariable("id") Long userId, @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getSalesByUser(userId, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/user/{id}/sales/time")
    public List<PurchaseDto> getSalesByUserInTimePurchases(
            @PathVariable("id") Long userId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return purchaseService.getSalesByUserInTime(userId, from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/advertisement/{id}")
    public List<PurchaseDto> getByAdvertisementPurchases(@PathVariable("id") Long advertisementId, @RequestBody List<ViewSortParameter> parameters) {
        return purchaseService.getByAdvertisement(advertisementId, parameters);
    }

    @PreAuthorize("hasAuthority('OP_VIEW_PURCHASE')")
    @GetMapping("/purchases/advertisement/{id}/time")
    public List<PurchaseDto> getByAdvertisementInTimePurchases(
            @PathVariable("id") Long advertisementId,
            @RequestParam("from") LocalDateTime from, @RequestParam("to") LocalDateTime to,
            @RequestBody List<ViewSortParameter> parameters
    ) {
        return purchaseService.getByAdvertisementInTime(advertisementId, from, to, parameters);
    }

    @PreAuthorize("hasAuthority('OP_DELETE_RATING')")
    @DeleteMapping("/ratings/{id}")
    public Boolean deleteRating(@PathVariable("id") Long id) {
        return ratingService.delete(id);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_USER')")
    @PutMapping("/users/{id}")
    public UserDto updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateDto user) {
        return userService.update(id, user);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_USER')")
    @PatchMapping("/users/{id}")
    public UserDto partiallyUpdateUser(@PathVariable("id") Long id, @RequestBody List<ViewUpdateParameter> parameters) {
        return userService.partiallyUpdate(id, parameters);
    }

    @PreAuthorize("hasAuthority('OP_UPDATE_USER')")
    @DeleteMapping("/users/{id}")
    public UserDto deleteUser(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_HELPER', 'ROLE_MODERATOR', 'ROLE_ADMIN')")
    @GetMapping("/users/{id}/roles")
    public List<RoleDto> getRolesUser(@PathVariable("id") Long id) {
        return userService.getRoles(id);
    }
}
