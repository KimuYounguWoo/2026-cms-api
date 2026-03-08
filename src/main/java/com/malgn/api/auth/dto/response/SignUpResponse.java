package com.malgn.api.auth.dto.response;

import com.malgn.api.auth.dto.request.SignUpRequest;
import com.malgn.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponse {

    private final String username;
    private final UserRole role;

    @Builder
    public SignUpResponse(String username, UserRole role) {
        this.username = username;
        this.role = role;
    }

    public static SignUpResponse of(SignUpRequest signUpRequest) {
        return SignUpResponse.builder().username(signUpRequest.getUsername()).role(signUpRequest.getRole()).build();
    }

}
