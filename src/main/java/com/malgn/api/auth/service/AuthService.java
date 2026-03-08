package com.malgn.api.auth.service;

import com.malgn.api.auth.dto.request.LoginRequest;
import com.malgn.api.auth.dto.request.SignUpRequest;
import com.malgn.api.auth.dto.response.LoginResponse;
import com.malgn.api.auth.dto.response.LogoutResponse;
import com.malgn.api.auth.dto.response.ReissueResponse;
import com.malgn.api.auth.dto.response.SignUpResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequestDto);
    ReissueResponse reissue(String refreshToken);
    LogoutResponse logout(String accessToken);
    SignUpResponse signup(SignUpRequest signUpRequest);
}
