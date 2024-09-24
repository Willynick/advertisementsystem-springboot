package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.AdvertisementRepository;
import com.senlainc.advertisementsystem.dao.OrderRepository;
import com.senlainc.advertisementsystem.dao.ProfileRepository;
import com.senlainc.advertisementsystem.dto.order.OrderDto;
import com.senlainc.advertisementsystem.dto.order.OrderUpdateDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.OrderMapper;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.order.*;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.serviceapi.OrderService;
import com.senlainc.advertisementsystem.validatorapi.AdvertisementValidator;
import com.senlainc.advertisementsystem.validatorapi.OrderValidator;
import com.senlainc.advertisementsystem.validatorapi.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProfileRepository profileRepository;
    private final AdvertisementRepository advertisementDao;
    private final OrderMapper orderMapper;
    private final OrderValidator orderValidator;
    private final ProfileValidator profileValidator;
    private final AdvertisementValidator advertisementValidator;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProfileRepository profileRepository,
                            AdvertisementRepository advertisementDao,
                            OrderMapper orderMapper, OrderValidator orderValidator, ProfileValidator profileValidator,
                            AdvertisementValidator advertisementValidator) {
        this.orderRepository = orderRepository;
        this.profileRepository = profileRepository;
        this.advertisementDao = advertisementDao;
        this.orderMapper = orderMapper;
        this.orderValidator = orderValidator;
        this.profileValidator = profileValidator;
        this.advertisementValidator = advertisementValidator;
    }

    @Override
    public OrderDto add(Object principal, List<Long> advertisementsIds) {
        orderValidator.checkListAdvertisements(advertisementsIds.size());

        Order order = new Order();
        List<OrderStatusModel> orderStatuses = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        Profile profile = profileRepository.getByUserId(((JwtUser) principal).getId());
        profileValidator.checkNotNull(profile);

        orderStatuses.add(new OrderStatusModel(OrderStatus.NEW, now, order));

        order.setProfile(profile);
        order.setOrderStatuses(orderStatuses);
        order.setDate(now);
        order.setDeliveryType(DeliveryType.BY_MAIL);

        List<Purchase> purchases = new ArrayList<>();
        advertisementsIds.forEach(id -> {
            Advertisement advertisement = advertisementDao.getOne(id);
            advertisementValidator.checkNotNull(advertisement);
            purchases.add(
                Purchase.builder()
                        .advertisement(advertisementDao.getOne(id))
                        .order(order).build()
            );
        });
        order.setPurchases(purchases);

        orderRepository.create(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto update(Object principal, Long id, OrderUpdateDto orderDto) {
        orderValidator.checkPermission(((JwtUser) principal).getId(), id);
        Order order = orderRepository.getByPK(id);
        orderValidator.checkNotNull(order);

        orderMapper.map(orderDto, order);
        orderRepository.update(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto partiallyUpdate(Object principal, Long id, List<ViewUpdateParameter> parameters) {
        orderValidator.checkPermission(((JwtUser) principal).getId(), id);

        orderRepository.partiallyUpdate(Order_.id, id, Converter.convertUpdateParameter(Order_.class, parameters));
        return orderMapper.toDto(orderRepository.getByPK(id));
    }

    @Override
    public OrderDto delete(Object principal, Long id) {
        orderValidator.checkPermission(((JwtUser) principal).getId(), id);
        return delete(id);
    }

    @Override
    public OrderDto delete(Long id) {
        Order order = orderRepository.getByPK(id);
        orderValidator.checkNotNull(order);

        List<OrderStatusModel> orderStatuses = order.getOrderStatuses();
        orderValidator.checkOrderCancelled(orderStatuses);

        orderStatuses.add(new OrderStatusModel(OrderStatus.CANCELED, LocalDateTime.now(), order));
        order.setOrderStatuses(orderStatuses);
        orderRepository.update(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto getById(Object principal, Long id) {
        orderValidator.checkPermission(((JwtUser) principal).getId(), id);
        return getById(id);
    }

    @Override
    public OrderDto getById(Long id) {
        Order order = orderRepository.getByPK(id);
        orderValidator.checkNotNull(order);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> get(List<ViewSortParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getAll(Converter.convertSortParameter(Order_.class, parameters)));
    }

    @Override
    public List<OrderDto> getByParameters(List<ViewGetParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getByParameters(Converter.convertGetParameter(Order_.class, parameters)));
    }

    @Override
    public List<OrderDto> getByUser(Object principal, List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getByUser(userId, parameters);
    }

    @Override
    public List<OrderDto> getByUser(Long userId, List<ViewSortParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getByProfileUserId(userId, Converter.convertSortParameter(Order_.class, parameters)));
    }

    @Override
    public List<OrderDto> getByUserInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                          List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getByUserInTime(userId, from, to, parameters);
    }

    @Override
    public List<OrderDto> getByUserInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                          List<ViewSortParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getByProfileUserIdAndDateBetween(userId, from, to, Converter.convertSortParameter(Order_.class, parameters)));
    }

    @Override
    public List<OrderDto> getCompleted(Object principal, List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getCompleted(userId, parameters);
    }

    @Override
    public List<OrderDto> getCompleted(Long userId, List<ViewSortParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getCompleted(userId, Converter.convertSortParameter(Order_.class, parameters)));
    }

    @Override
    public List<OrderDto> getCompletedAll(List<ViewSortParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getCompletedAll(Converter.convertSortParameter(Order_.class, parameters)));
    }

    @Override
    public List<OrderDto> getCompletedInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                             List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getCompletedInTime(userId, from, to, parameters);
    }

    @Override
    public List<OrderDto> getCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                             List<ViewSortParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getCompletedInTime(userId, from, to,
                        Converter.convertSortParameter(Order_.class, parameters)));
    }

    @Override
    public List<OrderDto> getCompletedInTimeAll(LocalDateTime from, LocalDateTime to,
                                                List<ViewSortParameter> parameters) {
        return orderMapper.toDtoList(
                orderRepository.getCompletedInTimeAll(from, to,
                        Converter.convertSortParameter(Order_.class, parameters)));
    }

    @Override
    public OrderDto setDeliveryType(Object principal, Long id, DeliveryType deliveryType) {
        Long userId = ((JwtUser) principal).getId();
        orderValidator.checkPermission(userId, id);

        Order order = orderRepository.getByPK(id);
        orderValidator.checkNotNull(order);
        List<OrderStatusModel> statuses = order.getOrderStatuses();
        orderValidator.checkOrderCompleted(statuses);
        orderValidator.checkOrderCancelled(statuses);
        order.setDeliveryType(deliveryType);
        orderRepository.update(order);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDto order(Object principal, Long id) {
        Long userId = ((JwtUser) principal).getId();
        orderValidator.checkPermission(userId, id);

        Order order = orderRepository.getByPK(id);
        orderValidator.checkNotNull(order);
        List<OrderStatusModel> statuses = order.getOrderStatuses();
        orderValidator.checkDeliveryType(order.getDeliveryType());
        orderValidator.checkOrderCompleted(statuses);
        orderValidator.checkOrderCancelled(statuses);
        orderValidator.checkAdvertisementsHidden(order.getPurchases());

        Profile profile = order.getProfile();

        double totalPrice = getTotalPrice(principal, id);
        LocalDateTime now  = LocalDateTime.now();
        if (totalPrice <= profile.getAmountOfMoney()) {
            statuses.add(new OrderStatusModel(OrderStatus.COMPLETED, now, order));
            order.setDate(now);
            moneyEarnedFieldSetValue(order);

            profile.setAmountOfMoney(profile.getAmountOfMoney() - totalPrice);
            profileRepository.save(profile);
        } else {
            statuses.add(new OrderStatusModel(OrderStatus.CANCELED, now, order));
        }
        order.setOrderStatuses(statuses);
        orderRepository.update(order);
        return orderMapper.toDto(order);
    }

    private void moneyEarnedFieldSetValue(Order order) {
        order.getPurchases().forEach(purchase -> purchase.setEarnedMoney(purchase.getAdvertisement().getPrice()));
    }

    @Override
    public Double getTotalPrice(Object principal, Long id) {
        orderValidator.checkPermission(((JwtUser) principal).getId(), id);
        return getTotalPrice(id);
    }

    @Override
    public Double getTotalPrice(Long id) {
        return orderRepository.getTotalPrice(id);
    }

    @Override
    public Double getAmountOfMoneyEarnedInTime(Object principal, LocalDateTime from, LocalDateTime to) {
        Long userId = ((JwtUser) principal).getId();
        return getAmountOfMoneyEarnedInTime(userId, from, to);
    }


    @Override
    public Double getAmountOfMoneyEarnedInTime(Long userId, LocalDateTime from, LocalDateTime to) {
        return orderRepository.getAmountOfMoneyEarnedInTime(userId, from, to);
    }

    @Override
    public Long getNumberCompletedInTime(Object principal, LocalDateTime from, LocalDateTime to) {
        Long userId = ((JwtUser) principal).getId();
        return getNumberCompletedInTime(userId, from, to);
    }

    @Override
    public Long getNumberCompletedInTime(Long userId, LocalDateTime from, LocalDateTime to) {
        return orderRepository.getNumberCompletedInTime(userId, from, to);
    }
}
