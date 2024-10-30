package com.sparta.schedule_project.token;

import com.sparta.schedule_project.emums.LoginType;
import com.sparta.schedule_project.emums.TokenType;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProviderManager {
    public final String LOGIN_TYPE_HEADER = "LoginType";
    private final TokenType tokenType = TokenType.HEADER;
    private final JwtCookieTokenProvider jwtCookieTokenProvider;
    private final JwtHeaderTokenProvider jwtHeaderTokenProvider;

    public TokenProvider getTokenProvider(HttpServletRequest req) {
//        LoginType loginType = LoginType.valueOf(req.getAttribute(LOGIN_TYPE_HEADER).toString());

        if(tokenType == TokenType.COOKIE) {
            return jwtCookieTokenProvider;
        }
        else {
            return jwtHeaderTokenProvider;
        }
    }

    public TokenProvider getTokenProvider(String url) {
//        LoginType loginType = LoginType.JWT;
//        if(url.contains("/api/login"))
//            loginType = LoginType.JWT;

        if(tokenType == TokenType.COOKIE) {
            return jwtCookieTokenProvider;
        }
        else {
            return jwtHeaderTokenProvider;
        }
    }
}
