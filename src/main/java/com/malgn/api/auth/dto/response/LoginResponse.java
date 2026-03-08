package com.malgn.api.auth.dto.response;

import com.malgn.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;
    private final String username;
    private final UserRole role;

    @Builder
    public LoginResponse(
            String accessToken, String refreshToken, String username, UserRole role
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.role = role;
    }

    public static LoginResponse of(
            String accessToken, String refreshToken, String username, UserRole role
    ) {
        return LoginResponse.builder().accessToken(accessToken).refreshToken(refreshToken).username(username).role(role).build();
    }
}
