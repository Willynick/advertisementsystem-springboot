package com.senlainc.advertisementsystem.jwtsecutiry.userdetails;

import com.senlainc.advertisementsystem.dao.UserRepository;
import com.senlainc.advertisementsystem.daospec.old.UserSpecification;
import com.senlainc.advertisementsystem.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Unknown user: " + username);
        }

        return JwtUserFactory.create(user);
    }
}
