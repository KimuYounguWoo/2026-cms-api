package com.malgn.api.auth.service;

import com.malgn.domain.user.entity.User;
import com.malgn.domain.user.repository.UserRepository;
import com.malgn.exception.CustomException;
import com.malgn.exception.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CustomException(ResponseCode.MEMBER_NOT_FOUND));
        UserPrincipal userPrincipal = UserPrincipal.toPrincipal(user);

        return new CustomUserDetails(userPrincipal);
    }
}