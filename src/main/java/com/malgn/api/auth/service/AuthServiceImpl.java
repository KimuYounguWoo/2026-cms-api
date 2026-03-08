package com.malgn.api.auth.service;

import com.malgn.api.auth.dto.request.LoginRequest;
import com.malgn.api.auth.dto.request.SignUpRequest;
import com.malgn.api.auth.dto.response.LoginResponse;
import com.malgn.api.auth.dto.response.LogoutResponse;
import com.malgn.api.auth.dto.response.ReissueResponse;
import com.malgn.api.auth.dto.response.SignUpResponse;
import com.malgn.configure.jwt.JwtUtil;
import com.malgn.configure.redis.RedisUtil;
import com.malgn.domain.user.entity.User;
import com.malgn.domain.user.repository.UserRepository;
import com.malgn.exception.CustomException;
import com.malgn.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    @Value("${jwt.refresh_exp}")
    private Duration expireTime;


    @Override
    public LogoutResponse logout(String accessToken) {
        String token = jwtUtil.splitBearerToken(accessToken);
        jwtUtil.validateToken(token);
        redisUtil.deleteValue(jwtUtil.getUsername(token));

        return LogoutResponse.of(
                jwtUtil.getUserId(token),
                jwtUtil.getUsername(token)
        );
    }

    @Override
    public ReissueResponse reissue(String refreshToken) {

        String token = jwtUtil.splitBearerToken(refreshToken);
        jwtUtil.validateToken(token);

        String username = jwtUtil.getUsername(token);

        String savedToken = redisUtil.getValue(username);

        if (savedToken == null || !savedToken.equals(token)) {
            throw new CustomException(ResponseCode.NOT_FOUND_REFRESH_TOKEN);
        }

        Long userId = jwtUtil.getUserId(token);

        UserPrincipal userPrincipal = UserPrincipal.toPrincipal(getUser(userId));

        String newRefreshToken = jwtUtil.createRefreshToken(userPrincipal);
        String newAccessToken = jwtUtil.createAccessToken(userPrincipal);

        redisUtil.deleteValue(jwtUtil.getUsername(token));
        redisUtil.setValues(userPrincipal.getUsername(), newRefreshToken, expireTime);

        return ReissueResponse.of(
                newAccessToken,
                newRefreshToken
        );
    }

    @Override
    public SignUpResponse signup(SignUpRequest signUpRequest) {
        boolean duplicateUsernameCheck = userRepository.findByUsername(signUpRequest.getUsername()).isPresent();
        if (duplicateUsernameCheck) {
            throw new CustomException(ResponseCode.DUPLICATE_USERNAME);
        }

        String encryptPassword = passwordEncoder.encode(signUpRequest.getPassword());
        User user = User.of(signUpRequest.getUsername(), encryptPassword, signUpRequest.getRole());
        userRepository.save(user);

        return SignUpResponse.of(signUpRequest);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(
                ResponseCode.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ResponseCode.PASSWORD_INCORRECT);
        }


        UserPrincipal userPrincipal = UserPrincipal.toPrincipal(user);
        String refreshToken = jwtUtil.createRefreshToken(userPrincipal);
        String accessToken = jwtUtil.createAccessToken(userPrincipal);
        redisUtil.setValues(userPrincipal.getUsername(), refreshToken, expireTime);

        return LoginResponse.of(
                accessToken,
                refreshToken,
                user.getUsername(),
                user.getRole()
        );
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));
    }
}
