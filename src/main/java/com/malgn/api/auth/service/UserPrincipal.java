package com.malgn.api.auth.service;

import com.malgn.domain.user.entity.User;
import com.malgn.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserPrincipal {
    private Long id;
    private String username;
    private String password;
    private UserRole role;

    @Builder
    public UserPrincipal(
            Long id,  String username, String password, UserRole role
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public static UserPrincipal toPrincipal(User user) {
        return UserPrincipal.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
