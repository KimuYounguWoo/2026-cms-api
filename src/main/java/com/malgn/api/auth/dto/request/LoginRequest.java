package com.malgn.api.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    @Builder
    public LoginRequest(
            String username, String password
    ) {
        this.username = username;
        this.password = password;
    }

    public static LoginRequest of(String username, String password) {
        return LoginRequest.builder().username(username).password(password).build();
    }
}
