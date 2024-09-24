package com.senlainc.advertisementsystem.serviceapi;

import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dto.user.UserDto;
import com.senlainc.advertisementsystem.dto.user.UserUpdateDto;
import com.senlainc.advertisementsystem.dto.user.role.RoleDto;
import com.senlainc.advertisementsystem.model.user.Language;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    UserDto add(UserUpdateDto userDto);
    UserDto update(Object principal, UserUpdateDto userDto);
    UserDto update(Long id, UserUpdateDto userDto);
    UserDto partiallyUpdate(Object principal, List<ViewUpdateParameter> parameters);
    UserDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters);
    UserDto delete(Object principal);
    UserDto delete(Long id);
    UserDto getById(Long id);
    List<UserDto> get(List<ViewSortParameter> parameters);
    UserDto getByUsername(String username);
    UserDto getByProfile(Long profileId);
    List<RoleDto> getRoles(Long id);
    List<UserDto> getByCity(Long cityId, List<ViewSortParameter> parameters);
    List<UserDto> getByCountry(Long countryId, List<ViewSortParameter> parameters);
    List<UserDto> getByLanguage(Language language, List<ViewSortParameter> parameters);
}
