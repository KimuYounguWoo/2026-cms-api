package com.malgn.api.auth.controller;

import com.malgn.api.auth.dto.request.LoginRequest;
import com.malgn.api.auth.dto.request.SignUpRequest;
import com.malgn.api.auth.dto.response.LoginResponse;
import com.malgn.api.auth.dto.response.LogoutResponse;
import com.malgn.api.auth.dto.response.ReissueResponse;
import com.malgn.api.auth.dto.response.SignUpResponse;
import com.malgn.api.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;


    // Guest Role

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    // 회원가입
    @PostMapping("/auth/sign-up")
    public ResponseEntity<SignUpResponse> signup(
            @RequestBody SignUpRequest signUpRequest
    ) {
        return ResponseEntity.ok(authService.signup(signUpRequest));
    }

    // User Role

    // 로그아웃
    @GetMapping("/user/auth/logout")
    public ResponseEntity<LogoutResponse> logout(
            @RequestHeader("Authorization") String accessToken
    ) {
        return ResponseEntity.ok(authService.logout(accessToken));
    }

    // 토큰 재발급
    @GetMapping("/user/auth/reissue")
    public ResponseEntity<ReissueResponse> reissue(
            @RequestHeader("Authorization") String refreshToken
    ) {
        return ResponseEntity.ok(authService.reissue(refreshToken));
    }
}