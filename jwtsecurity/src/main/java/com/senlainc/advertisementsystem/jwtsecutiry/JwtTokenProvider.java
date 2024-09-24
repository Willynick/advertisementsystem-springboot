package com.senlainc.advertisementsystem.jwtsecutiry;

import com.senlainc.advertisementsystem.jwtsecutiry.exception.JwtAuthenticationException;
import com.senlainc.advertisementsystem.model.user.Language;
import com.senlainc.advertisementsystem.model.user.role.Role;
import com.senlainc.advertisementsystem.model.user.role.permission.Permission;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private String secret;
    private byte[] signingKey;
    private final Long validity;
    private final UserDetailsService userDetailsService;

    private static final String JWT_AUTHENTICATION_EXCEPTION_MESSAGE = "JWT token is expired or invalid";

    @Autowired
    public JwtTokenProvider(
            @Value("${jwt.token.secret}") String secret,
            @Value("${jwt.token.validity}") Long validity,
            @Qualifier("jwtUserDetailsService") UserDetailsService userDetailsService) {
        this.secret = secret;
        this.validity = validity;
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
        signingKey = secret.getBytes();
    }

    public String createToken(String username, List<Role> roles, Language language) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getPermissionsNames(roles));
        claims.put("language", language);

        Date now = new Date();
        Date dateValidity = new Date(now.getTime() + validity);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(dateValidity)
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();
    }

    String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException(JWT_AUTHENTICATION_EXCEPTION_MESSAGE);
        }
    }

    private List<String> getPermissionsNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.forEach(role -> {
            result.add(role.getAuthority());
            result.addAll(role.getPermissions().stream().map(Permission::getAuthority).collect(Collectors.toList()));
        });

        return result;
    }
}
