package com.malgn.api.auth.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserPrincipal userPrincipal;

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_" + userPrincipal.getRole().toString());

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return userPrincipal.getPassword();
    }

    @Override
    @NullMarked
    public String getUsername() {
        return userPrincipal.getId().toString();
    }

    public Long getId() {
        return userPrincipal.getId();
    }
}