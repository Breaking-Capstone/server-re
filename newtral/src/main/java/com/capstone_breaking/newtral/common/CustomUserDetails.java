package com.capstone_breaking.newtral.common;

import com.capstone_breaking.newtral.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {
    private Long id;
    private String email;
    private List<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    private CustomUserDetails(Long id, String email, List<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.authorities = authorities;
    }

    public static CustomUserDetails from(User user) {
        List<SimpleGrantedAuthority> authorities = user.getRole().stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());

        return new CustomUserDetails(user.getId(),
                user.getEmail(),
                authorities);
    }

    public Long getId() {
        return id;
    }

    // UserDetail Override
    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return email;
    }

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
        return true;
    }

    // OAuth2User Override
    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


}
