package com.sparta.schedule_project.config;

import com.sparta.schedule_project.cookie.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TokenProviderConfig {
    private final TokenType tokenType = TokenType.JwtCookie;
    private final JwtUtil jwtUtil;

    @Bean
    public JwtTokenManager jwtTokenProvider() {
        return switch (tokenType) {
            case JwtCookie -> new JwtTokenByCookie(jwtUtil);
            case JwtHeader -> new JwtTokenByHeader(jwtUtil);
        };
    }
}
