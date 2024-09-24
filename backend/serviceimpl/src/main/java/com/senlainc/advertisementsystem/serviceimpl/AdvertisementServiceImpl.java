package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.AdvertisementRepository;
import com.senlainc.advertisementsystem.dao.ProfileRepository;
import com.senlainc.advertisementsystem.dao.PurchaseRepository;
import com.senlainc.advertisementsystem.daospec.AdvertisementSpecification;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementDto;
import com.senlainc.advertisementsystem.dto.advertisement.AdvertisementUpdateDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.AdvertisementMapper;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement;
import com.senlainc.advertisementsystem.model.advertisement.AdvertisementStatus;
import com.senlainc.advertisementsystem.model.advertisement.Advertisement_;
import com.senlainc.advertisementsystem.model.category.Category;
import com.senlainc.advertisementsystem.model.order.purchase.Purchase;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.serviceapi.AdvertisementService;
import com.senlainc.advertisementsystem.serviceimpl.comparators.AdvertisementStatusComparator;
import com.senlainc.advertisementsystem.validatorapi.AdvertisementValidator;
import com.senlainc.advertisementsystem.validatorapi.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementDao;
    private final ProfileRepository profileRepository;
    private final PurchaseRepository purchaseRepository;
    private final AdvertisementMapper advertisementMapper;
    private final Double amountForTop;
    private final AdvertisementValidator advertisementValidator;
    private final ProfileValidator profileValidator;

    @Autowired
    public AdvertisementServiceImpl(AdvertisementRepository advertisementDao, ProfileRepository profileRepository,
                                    PurchaseRepository purchaseRepository, AdvertisementMapper advertisementMapper,
                                    @Value("${advertisement.amountForTop}") Double amountForTop,
                                    AdvertisementValidator advertisementValidator, ProfileValidator profileValidator) {
        this.advertisementDao = advertisementDao;
        this.profileRepository = profileRepository;
        this.purchaseRepository = purchaseRepository;
        this.advertisementMapper = advertisementMapper;
        this.amountForTop = amountForTop;
        this.advertisementValidator = advertisementValidator;
        this.profileValidator = profileValidator;
    }

    @Override
    public AdvertisementDto add(Object principal, AdvertisementUpdateDto advertisementDto) {
        Profile profile = profileRepository.getByUserId(((JwtUser) principal).getId());
        profileValidator.checkNotNull(profile);

        Advertisement advertisement = advertisementMapper.toEntity(advertisementDto);
        advertisement.setProfile(profile);
        advertisement.setUploadDate(LocalDateTime.now());
        advertisement.setAdvertisementStatus(AdvertisementStatus.ACTIVE);
        advertisementDao.save(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    @Override
    public AdvertisementDto update(Object principal, Long id, AdvertisementUpdateDto advertisementDto) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), id);
        return update(id, advertisementDto);
    }

    @Override
    public AdvertisementDto update(Long id, AdvertisementUpdateDto advertisementDto) {
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        advertisementMapper.map(advertisementDto, advertisement);
        advertisement.setChangeDate(LocalDateTime.now());
        advertisementDao.save(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    @Override
    public AdvertisementDto partiallyUpdate(Object principal, Long id, List<ViewUpdateParameter> parameters) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), id);
        return partiallyUpdate(id, parameters);
    }

    @Override
    public AdvertisementDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters) {
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        advertisement.setChangeDate(LocalDateTime.now());
        advertisementDao.save(advertisement);
        advertisementDao.partiallyUpdate(Advertisement_.id, id,
                Converter.convertUpdateParameter(Advertisement_.class, parameters));
        return advertisementMapper.toDto(advertisementDao.getOne(id));
    }

    @Override
    public Boolean delete(Object principal, Long id) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), id);
        return delete(id);
    }

    @Override
    public Boolean delete(Long id) {
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        advertisementDao.delete(advertisement);
        return true;
    }

    @Override
    public AdvertisementDto getById(Long id) {
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        return advertisementMapper.toDto(advertisement);
    }

    @Override
    public List<AdvertisementDto> get(List<ViewSortParameter> parameters) {
        return advertisementMapper.toDtoList(advertisementDao.findAll(Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<AdvertisementDto> getByParameters(List<ViewGetParameter> parameters) {
        return advertisementMapper.toDtoList(
                advertisementDao.getByParameters(Converter.convertGetParameter(Advertisement_.class, parameters)));
    }

    @Override
    public List<AdvertisementDto> getByUser(Long userId, List<ViewSortParameter> parameters) {
        return advertisementMapper.toDtoList(
                advertisementDao.getByProfileUserId(userId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<AdvertisementDto> getByCategory(Long categoryId, List<ViewSortParameter> parameters) {
        return advertisementMapper.toDtoList(
                advertisementDao.getByCategoryId(categoryId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<AdvertisementDto> getInTheTop() {
        List<Advertisement> advertisements = advertisementDao.findAll(AdvertisementSpecification.getInTheTop());
        advertisements.sort(new AdvertisementStatusComparator(Sort.Direction.DESC));
        return advertisementMapper.toDtoList(advertisements);
    }

    @Override
    public List<AdvertisementDto> getByInterests(Object principal) {
        Map<Category, Integer> interests = new HashMap<>();
        List<Purchase> purchases = purchaseRepository.getByOrderProfileUserId(((JwtUser) principal).getId(), Sort.by(new ArrayList<>()));
        purchases.forEach(purchase -> {
            Category category = purchase.getAdvertisement().getCategory();
            if (interests.containsKey(category)) {
                interests.put(category, interests.get(category) + 1);
            } else {
                interests.put(category, 1);
            }
        });

        Set<Map.Entry<Category, Integer>> set = interests.entrySet().stream()
                .sorted(Map.Entry.<Category, Integer>comparingByValue().reversed()).collect(Collectors.toCollection(LinkedHashSet::new));

        List<Advertisement> advertisements = new ArrayList<>();
        set.forEach(map -> advertisements.addAll(map.getKey().getAdvertisements()));

        return advertisementMapper.toDtoList(advertisements);
    }

    @Override
    public List<AdvertisementDto> getByCity(Long cityId, List<ViewSortParameter> parameters) {
        return advertisementMapper.toDtoList(
                advertisementDao.getByAddressesCityId(cityId, Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<AdvertisementDto> getByCountry(Long countryId, List<ViewSortParameter> parameters) {
        return advertisementMapper.toDtoList(
                advertisementDao.findAll(AdvertisementSpecification.getByCountryId(countryId),
                        Converter.convertSortParameter(parameters)));
    }

    @Override
    public AdvertisementDto raiseInTheTop(Object principal, Long id, double amountOfMoney) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), id);
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        Profile profile = advertisement.getProfile();
        advertisementValidator.checkAdvertisementHidden(advertisement.getAdvertisementStatus());
        advertisementValidator.checkEnoughMoney(amountOfMoney, amountForTop, profile.getAmountOfMoney());

        profile.setAmountOfMoney(profile.getAmountOfMoney() - amountOfMoney);
        advertisement.setAdvertisementStatus(AdvertisementStatus.IN_THE_TOP);
        advertisementDao.save(advertisement);
        profileRepository.save(profile);
        return advertisementMapper.toDto(advertisement);
    }

    @Override
    public AdvertisementDto raiseInTheTop(Long id) {
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        advertisementValidator.checkAdvertisementHidden(advertisement.getAdvertisementStatus());
        advertisement.setAdvertisementStatus(AdvertisementStatus.IN_THE_TOP);
        advertisementDao.save(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    @Override
    public AdvertisementDto hide(Object principal, Long id) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), id);
        return hide(id);
    }

    @Override
    public AdvertisementDto hide(Long id) {
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        advertisement.setAdvertisementStatus(AdvertisementStatus.HIDDEN);
        advertisementDao.save(advertisement);
        return advertisementMapper.toDto(advertisement);
    }

    @Override
    public AdvertisementDto restore(Object principal, Long id) {
        advertisementValidator.checkPermission(((JwtUser) principal).getId(), id);
        return restore(id);
    }

    @Override
    public AdvertisementDto restore(Long id) {
        Advertisement advertisement = advertisementDao.getOne(id);
        advertisementValidator.checkNotNull(advertisement);

        advertisement.setAdvertisementStatus(AdvertisementStatus.ACTIVE);
        advertisementDao.save(advertisement);
        return advertisementMapper.toDto(advertisement);
    }
}
