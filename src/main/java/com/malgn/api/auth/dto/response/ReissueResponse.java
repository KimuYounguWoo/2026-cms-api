package com.malgn.api.auth.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueResponse {

    private final String accessToken;
    private final String refreshToken;

    @Builder
    public ReissueResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static ReissueResponse of(String accessToken, String refreshToken) {
        return ReissueResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }
}
