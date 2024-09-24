package com.senlainc.advertisementsystem.serviceimpl;

import com.senlainc.advertisementsystem.backendutils.constants.Constants;
import com.senlainc.advertisementsystem.dao.UserRepository;
import com.senlainc.advertisementsystem.daospec.old.UserSpecification;
import com.senlainc.advertisementsystem.dto.authentication.AuthenticationDto;
import com.senlainc.advertisementsystem.jwtsecutiry.JwtTokenProvider;
import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.serviceapi.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                                     UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public ResponseEntity login(AuthenticationDto authenticationDto) {
        try {
            String username = authenticationDto.getUsername();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, authenticationDto.getPassword())
            );
            User user = userRepository.getByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException(String.format(Constants.USERNAME_NOT_FOUND_MESSAGE, username));
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles(), user.getLanguage());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException(Constants.BAD_CREDENTIALS_MESSAGE);
        }
    }
}
