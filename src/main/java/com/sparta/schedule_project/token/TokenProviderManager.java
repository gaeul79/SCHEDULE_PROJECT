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
    public final String LOGIN_TYPE_HEADER = "LoginType";
    private final TokenType tokenType = TokenType.COOKIE;
    private final JwtCookieTokenProvider jwtCookieTokenProvider;
    private final JwtHeaderTokenProvider jwtHeaderTokenProvider;

    /**
     * HTTP 요청에 따라 토큰 제공자를 선택합니다.
     *
     * @param req HTTP 요청 객체
     * @return 선택된 토큰 제공자
     * @since 2024-10-31
     */
    public TokenProvider getTokenProvider(HttpServletRequest req) {
//        LoginType loginType = LoginType.valueOf(req.getAttribute(LOGIN_TYPE_HEADER).toString());

        if (tokenType == TokenType.COOKIE) {
            return jwtCookieTokenProvider;
        } else {
            return jwtHeaderTokenProvider;
        }
    }

    /**
     * URL에 따라 토큰 제공자를 선택합니다.
     *
     * @param url URL
     * @return 선택된 토큰 제공자
     * @since 2024-10-31
     */
    public TokenProvider getTokenProvider(String url) {
//        LoginType loginType = LoginType.JWT;
//        if(url.contains("/api/login"))
//            loginType = LoginType.JWT;

        if (tokenType == TokenType.COOKIE) {
            return jwtCookieTokenProvider;
        } else {
            return jwtHeaderTokenProvider;
        }
    }
}
