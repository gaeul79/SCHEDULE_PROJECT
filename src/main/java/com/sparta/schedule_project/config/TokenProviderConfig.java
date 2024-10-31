package com.sparta.schedule_project.config;


import com.sparta.schedule_project.emums.TokenType;
import com.sparta.schedule_project.token.JwtCookieTokenProvider;
import com.sparta.schedule_project.token.JwtHeaderTokenProvider;
import com.sparta.schedule_project.token.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
