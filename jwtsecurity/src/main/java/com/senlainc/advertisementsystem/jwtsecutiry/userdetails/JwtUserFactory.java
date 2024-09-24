package com.senlainc.advertisementsystem.jwtsecutiry.userdetails;

import com.senlainc.advertisementsystem.model.user.User;
import com.senlainc.advertisementsystem.model.user.UserStatus;
import com.senlainc.advertisementsystem.model.user.role.Role;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getLanguage(),
                user.getStatus().equals(UserStatus.ACTIVE),
                mapToGrantedAuthorities(user.getRoles())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        roles.forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            grantedAuthorities.addAll(role.getPermissions().stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getAuthority())).collect(Collectors.toList()));
        });
        return grantedAuthorities;
    }
}
