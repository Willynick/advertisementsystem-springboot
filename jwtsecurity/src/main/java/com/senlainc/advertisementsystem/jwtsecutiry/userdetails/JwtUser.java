package com.senlainc.advertisementsystem.jwtsecutiry.userdetails;

import com.senlainc.advertisementsystem.model.user.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class JwtUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Language language;
    private boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
