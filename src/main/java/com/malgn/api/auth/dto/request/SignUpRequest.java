package com.malgn.api.auth.dto.request;

import com.malgn.domain.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private UserRole role;

    @Builder
    public SignUpRequest(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static SignUpRequest of(
            String username, String password, UserRole role) {
        return SignUpRequest.builder().username(username).password(password).role(role).build();
    }

}
