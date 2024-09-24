package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.ProfileRepository;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileDto;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileUpdateDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.ProfileMapper;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.model.user.profile.Profile_;
import com.senlainc.advertisementsystem.serviceapi.ProfileService;
import com.senlainc.advertisementsystem.validatorapi.ProfileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final ProfileValidator profileValidator;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper, ProfileValidator profileValidator) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.profileValidator = profileValidator;
    }

    @Override
    public ProfileDto update(Object principal, ProfileUpdateDto profileDto) {
        Long userId = ((JwtUser) principal).getId();
        return update(userId, profileDto);
    }

    @Override
    public ProfileDto update(Long userId, ProfileUpdateDto profileDto) {
        Profile profile = profileRepository.getByUserId(userId);
        profileValidator.checkNotNull(profile);

        profileMapper.map(profileDto, profile);
        profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    @Override
    public ProfileDto partiallyUpdate(Object principal, List<ViewUpdateParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return partiallyUpdate(userId, parameters);
    }

    @Override
    public ProfileDto partiallyUpdate(Long userId, List<ViewUpdateParameter> parameters) {
        Profile profile = profileRepository.getByUserId(userId);
        profileValidator.checkNotNull(profile);

        profileRepository.partiallyUpdate(Profile_.id, profile.getId(), Converter.convertUpdateParameter(Profile_.class, parameters));
        return profileMapper.toDto(profileRepository.getByUserId(userId));
    }

    @Override
    public ProfileDto delete(Object principal) {
        Long userId = ((JwtUser) principal).getId();
        return delete(userId);
    }

    @Override
    public ProfileDto delete(Long userId) {
        Profile profile = profileRepository.getByUserId(userId);
        profileValidator.checkNotNull(profile);

        Profile newProfile = new Profile();
        newProfile.setUser(profile.getUser());
        newProfile.setId(profile.getId());
        newProfile.setAmountOfMoney(profile.getAmountOfMoney());
        profile = newProfile;
        profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }

    @Override
    public ProfileDto getById(Long id) {
        Profile profile = profileRepository.getOne(id);
        profileValidator.checkNotNull(profile);

        return profileMapper.toDto(profile);
    }

    @Override
    public List<ProfileDto> get(List<ViewSortParameter> parameters) {
        return profileMapper.toDtoList(profileRepository.findAll(Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<ProfileDto> getByParameters(List<ViewGetParameter> parameters) {
        return profileMapper.toDtoList(
                profileRepository.getByParameters(Converter.convertGetParameter(Profile_.class, parameters)));
    }

    @Override
    public ProfileDto getByUser(Long userId) {
        Profile profile = profileRepository.getByUserId(userId);
        profileValidator.checkNotNull(profile);

        return profileMapper.toDto(profile);
    }

    @Override
    public ProfileDto getByUsername(String username) {
        Profile profile = profileRepository.getByUserUsername(username);
        profileValidator.checkNotNull(profile);

        return profileMapper.toDto(profile);
    }

    @Override
    public List<ProfileDto> getByCity(Long cityId, List<ViewSortParameter> parameters) {
        return profileMapper.toDtoList(profileRepository.getByAddressCityId(cityId,
                Converter.convertSortParameter(parameters)));
    }

    @Override
    public List<ProfileDto> getByCountry(Long countryId, List<ViewSortParameter> parameters) {
        return profileMapper.toDtoList(profileRepository.getByAddressCityCountryId(countryId,
                Converter.convertSortParameter(parameters)));
    }

    @Override
    public ProfileDto addMoney(Object principal, double amountOfMoney) {
        Long userId = ((JwtUser) principal).getId();
        return addMoney(userId, amountOfMoney);
    }

    @Override
    public ProfileDto addMoney(Long userId, double amountOfMoney) {
        Profile profile = profileRepository.getByUserId(userId);
        profileValidator.checkNotNull(profile);

        profile.setAmountOfMoney(profile.getAmountOfMoney() + amountOfMoney);
        profileRepository.save(profile);
        return profileMapper.toDto(profile);
    }
}
