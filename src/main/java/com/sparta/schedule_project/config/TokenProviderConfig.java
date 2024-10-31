package com.sparta.schedule_project.config;

import com.sparta.schedule_project.emums.TokenType;
import com.sparta.schedule_project.util.token.JwtCookieTokenProvider;
import com.sparta.schedule_project.util.token.JwtHeaderTokenProvider;
import com.sparta.schedule_project.util.token.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TokenProvider 설정 클래스
 *
 * @since 2024-10-31
 */
@Configuration
public class TokenProviderConfig {
    private final TokenType tokenType = TokenType.COOKIE;

    @Bean
    public TokenProvider tokenProvider() {
        if (tokenType == TokenType.COOKIE)
            return new JwtCookieTokenProvider();
        else
            return new JwtHeaderTokenProvider();
    }
}
