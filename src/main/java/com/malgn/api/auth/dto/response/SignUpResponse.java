package com.malgn.api.auth.dto.response;

import com.malgn.api.auth.dto.request.SignUpRequest;
import com.malgn.domain.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponse {

    @NotBlank
    private String username;

    @NotBlank
    private UserRole role;

    @Builder
    public SignUpResponse(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public static SignUpResponse of(SignUpRequest signUpRequest) {
        return SignUpResponse.builder().username(signUpRequest.getUsername()).role(signUpRequest.getRole()).build();
    }

}
