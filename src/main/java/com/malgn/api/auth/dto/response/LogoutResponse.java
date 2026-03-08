package com.malgn.api.auth.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class LogoutResponse {

    private final Long userId;
    private final String username;

    @Builder
    public LogoutResponse(Long userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public static LogoutResponse of(Long userId, String username){
        return LogoutResponse.builder().userId(userId).username(username).build();
    }
}
