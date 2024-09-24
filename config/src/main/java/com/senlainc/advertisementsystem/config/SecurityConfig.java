package com.senlainc.advertisementsystem.config;

import com.senlainc.advertisementsystem.exceptionhandling.ConfigAuthenticationEntryPoint;
import com.senlainc.advertisementsystem.jwtsecutiry.JwtConfigurer;
import com.senlainc.advertisementsystem.jwtsecutiry.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/auth/login";
    private static final String MAIN_ENDPOINT = "/";
    private static final String REGISTER_ENDPOINT = "/users/register";
    private static final String ADMIN_ENDPOINT = "/admin";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final ConfigAuthenticationEntryPoint configAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(
            JwtTokenProvider jwtTokenProvider,
            @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService,
            ConfigAuthenticationEntryPoint configAuthenticationEntryPoint
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.configAuthenticationEntryPoint = configAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT, MAIN_ENDPOINT, REGISTER_ENDPOINT).permitAll()
                .antMatchers(ADMIN_ENDPOINT).hasAnyRole(new String[]{"ROLE_HELPER", "ROLE_ADMIN", "ROLE_USER"})
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider))
                .and()
                .exceptionHandling().authenticationEntryPoint(configAuthenticationEntryPoint);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
