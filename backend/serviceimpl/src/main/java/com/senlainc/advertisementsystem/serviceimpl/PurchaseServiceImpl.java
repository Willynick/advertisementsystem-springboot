package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.dao.AdvertisementRepository;
import com.senlainc.advertisementsystem.dao.OrderRepository;
import com.senlainc.advertisementsystem.dao.PurchaseRepository;
import com.senlainc.advertisementsystem.dto.order.purchase.PurchaseDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.PurchaseMapper;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.order.Order;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.serviceapi.PurchaseService;
import com.senlainc.advertisementsystem.validatorapi.AdvertisementValidator;
import com.senlainc.advertisementsystem.validatorapi.OrderValidator;
import com.senlainc.advertisementsystem.validatorapi.PurchaseValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final OrderRepository orderRepository;
    private final AdvertisementRepository advertisementDao;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseValidator purchaseValidator;
    private final OrderValidator orderValidator;
    private final AdvertisementValidator advertisementValidator;

    @Autowired
    public PurchaseServiceImpl(PurchaseRepository purchaseRepository, OrderRepository orderRepository, AdvertisementRepository advertisementDao,
                               PurchaseMapper purchaseMapper, PurchaseValidator purchaseValidator,
                               OrderValidator orderValidator, AdvertisementValidator advertisementValidator) {
        this.purchaseRepository = purchaseRepository;
        this.orderRepository = orderRepository;
        this.advertisementDao = advertisementDao;
        this.purchaseMapper = purchaseMapper;
        this.purchaseValidator = purchaseValidator;
        this.orderValidator = orderValidator;
        this.advertisementValidator = advertisementValidator;
    }

    @Override
    public PurchaseDto add(Object principal, Long orderId, Long advertisementId) {
        orderValidator.checkPermission(((JwtUser) principal).getId(), orderId);
        Order order = orderRepository.getByPK(orderId);
        Advertisement advertisement = advertisementDao.getOne(advertisementId);

        orderValidator.checkNotNull(order);
        advertisementValidator.checkNotNull(advertisement);
        orderValidator.checkOrderCompleted(order.getOrderStatuses());
        orderValidator.checkOrderCancelled(order.getOrderStatuses());
        advertisementValidator.checkAdvertisementHidden(advertisement.getAdvertisementStatus());

        Purchase purchase = Purchase.builder().order(order).advertisement(advertisement).build();
        purchaseRepository.save(purchase);
        return purchaseMapper.toDto(purchase);
    }

    @Override
    public Boolean delete(Object principal, Long id) {
        purchaseValidator.checkPermission(((JwtUser) principal).getId(), id);
        return delete(id);
    }

    @Override
    public Boolean delete(Long id) {
        Purchase purchase = purchaseRepository.getOne(id);
        purchaseValidator.checkNotNull(purchase);

        purchaseRepository.delete(purchase);
        return true;
    }

    @Override
    public PurchaseDto getById(Object principal, Long id) {
        purchaseValidator.checkPermission(((JwtUser) principal).getId(), id);
        return getById(id);
    }

    @Override
    public PurchaseDto getById(Long id) {
        Purchase purchase = purchaseRepository.getOne(id);
        purchaseValidator.checkNotNull(purchase);

        return purchaseMapper.toDto(purchase);
    }

    @Override
    public List<PurchaseDto> get(List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.findAll(Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<PurchaseDto> getByOrder(Object principal, Long orderId, List<ViewSortParameter> parameters) {
        orderValidator.checkPermission(((JwtUser) principal).getId(), orderId);
        return getByOrder(orderId, parameters);
    }

    @Override
    public List<PurchaseDto> getByOrder(Long orderId, List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.getByOrderId(orderId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<PurchaseDto> getByUser(Object principal, List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getByUser(userId, parameters);
    }

    @Override
    public List<PurchaseDto> getByUser(Long userId, List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.getByOrderProfileUserId(userId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<PurchaseDto> getByUserInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                             List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getByUserInTime(userId, from, to, parameters);
    }

    @Override
    public List<PurchaseDto> getByUserInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                             List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.getByOrderProfileUserIdAndOrderDateBetween(userId, from, to,
                        Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<PurchaseDto> getSalesByUser(Object principal, List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getSalesByUser(userId, parameters);
    }

    @Override
    public List<PurchaseDto> getSalesByUser(Long userId, List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.getByAdvertisementProfileUserId(userId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<PurchaseDto> getSalesByUserInTime(Object principal, LocalDateTime from, LocalDateTime to,
                                                  List<ViewSortParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return getSalesByUserInTime(userId, from, to, parameters);
    }

    @Override
    public List<PurchaseDto> getSalesByUserInTime(Long userId, LocalDateTime from, LocalDateTime to,
                                                  List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.getByAdvertisementProfileUserIdAndOrderDateBetween(userId, from, to,
                        Converter.convertSortParameter(parameters)));
}

    @Override
    public List<PurchaseDto> getByAdvertisement(Object principal, Long advertisementId, List<ViewSortParameter> parameters) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), advertisementId);
        return getByAdvertisement(advertisementId, parameters);
    }

    @Override
    public List<PurchaseDto> getByAdvertisement(Long advertisementId, List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.getByAdvertisementId(advertisementId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<PurchaseDto> getByAdvertisementInTime(Object principal, Long advertisementId, LocalDateTime from,
                                                      LocalDateTime to, List<ViewSortParameter> parameters) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), advertisementId);
        return getByAdvertisementInTime(advertisementId, from, to, parameters);
    }

    @Override
    public List<PurchaseDto> getByAdvertisementInTime(Long advertisementId, LocalDateTime from,
                                                      LocalDateTime to, List<ViewSortParameter> parameters) {
        return purchaseMapper.toDtoList(
                purchaseRepository.getByAdvertisementIdAndOrderDateBetween(advertisementId, from, to,
                        Converter.convertSortParameter(parameters)));
    }
}
