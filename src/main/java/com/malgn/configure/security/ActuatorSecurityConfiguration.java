package com.malgn.configure.security;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@Order(1)
public class ActuatorSecurityConfiguration {

    String[] userWhiteList = {"/actuator/health", "/actuator/info"};

    @Bean
    public SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/actuator/**")
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(userWhiteList).permitAll()
                        .anyRequest().hasRole("ADMIN")
                )
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

}
