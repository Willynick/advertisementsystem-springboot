package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.Converter;
import com.senlainc.advertisementsystem.backendutils.ViewSortParameter;
import com.senlainc.advertisementsystem.backendutils.ViewUpdateParameter;
import com.senlainc.advertisementsystem.dao.RoleRepository;
import com.senlainc.advertisementsystem.dao.UserRepository;
import com.senlainc.advertisementsystem.daospec.old.RoleSpecification;
import com.senlainc.advertisementsystem.dto.user.UserDto;
import com.senlainc.advertisementsystem.dto.user.UserUpdateDto;
import com.senlainc.advertisementsystem.dto.user.role.RoleDto;
import com.senlainc.advertisementsystem.jwtsecutiry.userdetails.JwtUser;
import com.senlainc.advertisementsystem.mapper.RoleMapper;
import com.senlainc.advertisementsystem.mapper.UserMapper;
import com.senlainc.advertisementsystem.model.user.Language;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.UserStatus;
import com.senlainc.advertisementsystem.model.user.User_;
import com.senlainc.advertisementsystem.model.user.profile.Profile;
import com.senlainc.advertisementsystem.model.user.role.Role;
import com.senlainc.advertisementsystem.serviceapi.UserService;
import com.senlainc.advertisementsystem.validatorapi.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper,
                           RoleMapper roleMapper, BCryptPasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @Override
    public UserDto add(UserUpdateDto userDto) {
        User userFromDb = userRepository.getByUsername(userDto.getUsername());
        userValidator.checkIsExistsEntity(userFromDb);

        User user = userMapper.toEntity(userDto);
        Role role = roleRepository.getByName("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Profile profile = new Profile();
        profile.setUser(user);
        user.setStatus(UserStatus.ACTIVE);
        profile.setAverageRating(0);

        user.setProfile(profile);
        userRepository.create(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto update(Object principal, UserUpdateDto userDto) {
        Long userId = ((JwtUser) principal).getId();
        return update(userId, userDto);
    }

    @Override
    public UserDto update(Long id, UserUpdateDto userDto) {
        User user = userRepository.getByPK(id);
        userValidator.checkNotNull(user);

        userMapper.map(userDto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto partiallyUpdate(Object principal, List<ViewUpdateParameter> parameters) {
        Long userId = ((JwtUser) principal).getId();
        return partiallyUpdate(userId, parameters);
    }

    @Override
    public UserDto partiallyUpdate(Long id, List<ViewUpdateParameter> parameters) {
        userRepository.partiallyUpdate(User_.id, id, Converter.convertUpdateParameter(User_.class, parameters));
        return userMapper.toDto(userRepository.getByPK(id));
    }

    @Override
    public UserDto delete(Object principal) {
        Long userId = ((JwtUser) principal).getId();
        return delete(userId);
    }

    @Override
    public UserDto delete(Long id) {
        User user = userRepository.getByPK(id);
        userValidator.checkNotNull(user);

        user.setStatus(UserStatus.DELETED);
        userRepository.update(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.getByPK(id);
        userValidator.checkNotNull(user);

        return userMapper.toDto(user);
    }

    @Override
    public List<UserDto> get(List<ViewSortParameter> parameters) {
        return userMapper.toDtoList(userRepository.getAll(Converter.convertSortParameter(User_.class, parameters)));
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.getByUsername(username);
        userValidator.checkNotNull(user);

        return userMapper.toDto(user);
    }

    @Override
    public UserDto getByProfile(Long profileId) {
        User user = userRepository.getByProfileId(profileId);
        userValidator.checkNotNull(user);

        return userMapper.toDto(user);
    }

    @Override
    public List<RoleDto> getRoles(Long id) {
        return roleMapper.toDtoList(roleRepository.getByUsersId(id));
    }

    @Override
    public List<UserDto> getByCity(Long cityId, List<ViewSortParameter> parameters) {
        return userMapper.toDtoList(userRepository.getByProfileAddressCityId(cityId,
                Converter.convertSortParameter(User_.class, parameters)));
    }

    @Override
    public List<UserDto> getByCountry(Long countryId, List<ViewSortParameter> parameters) {
        return userMapper.toDtoList(userRepository.getByProfileAddressCityCountryId(countryId,
                Converter.convertSortParameter(User_.class, parameters)));
    }

    @Override
    public List<UserDto> getByLanguage(Language language, List<ViewSortParameter> parameters) {
        return userMapper.toDtoList(userRepository.getByLanguage(language,
                Converter.convertSortParameter(User_.class, parameters)));
    }
}
