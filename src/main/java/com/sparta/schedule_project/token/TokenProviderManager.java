package com.sparta.schedule_project.token;

import com.sparta.schedule_project.emums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * HTTP 요청에 따라 토큰 제공자를 선택합니다.
 *
 * @since 2024-10-31
 */
@Component
@RequiredArgsConstructor
public class TokenProviderManager {
    private final TokenType tokenType = TokenType.COOKIE;
    private final JwtCookieTokenProvider jwtCookieTokenProvider;
    private final JwtHeaderTokenProvider jwtHeaderTokenProvider;

    /**
     * HTTP 요청에 따라 토큰 제공자를 선택합니다.
     *
     * @return 선택된 토큰 제공자
     * @since 2024-10-31
     */
    public TokenProvider getTokenProvider() {
        if (tokenType == TokenType.COOKIE) {
            return jwtCookieTokenProvider;
        } else {
            return jwtHeaderTokenProvider;
        }
    }
}
