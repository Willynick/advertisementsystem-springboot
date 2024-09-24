package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewGetParameter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileDto;
import com.senlainc.advertisementsystem.dto.user.profile.ProfileUpdateDto;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ProfileService {

    ProfileDto update(Object principal, ProfileUpdateDto profileDto);
    ProfileDto update(Long userId, ProfileUpdateDto profileDto);
    ProfileDto partiallyUpdate(Object principal, List<ViewUpdateParameter> parameters);
    ProfileDto partiallyUpdate(Long userId, List<ViewUpdateParameter> parameters);
    ProfileDto delete(Object principal);
    ProfileDto delete(Long userId);
    ProfileDto getById(Long id);
    List<ProfileDto> get(List<ViewSortParameter> parameters);
    List<ProfileDto> getByParameters(List<ViewGetParameter> parameters);
    ProfileDto getByUser(Long userId);
    ProfileDto getByUsername(String username);
    List<ProfileDto> getByCity(Long cityId, List<ViewSortParameter> parameters);
    List<ProfileDto> getByCountry(Long countryId, List<ViewSortParameter> parameters);
    ProfileDto addMoney(Object principal, double amountOfMoney);
    ProfileDto addMoney(Long userId, double amountOfMoney);
}
