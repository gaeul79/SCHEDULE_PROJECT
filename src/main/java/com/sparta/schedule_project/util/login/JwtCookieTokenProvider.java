package com.sparta.schedule_project.util.login;

import com.sparta.schedule_project.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * JWT(JSON Web Token)를 이용하여 쿠키에 사용자 정보를 저장하고 인증을 처리하는 클래스입니다.
 *
 * @since 2024-10-18
 */
@RequiredArgsConstructor
@Component
public class JwtCookieTokenProvider extends JwtUtil implements TokenProvider {
    /**
     * JWT 토큰을 생성하여 쿠키에 추가하는 메소드입니다.
     *
     * @param res  HTTP 응답 객체
     * @param user 사용자 정보
     * @since 2024-10-18
     */
    @Override
    public void setToken(HttpServletResponse res, User user) {
        String token = this.createToken(user.getEmail(), user.getAuth());
        token = URLEncoder
                .encode(token, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");

        res.addCookie(cookie); // Response 객체에 Cookie 추가
    }

    /**
     * HTTP 요청에서 쿠키 값으로 JWT 토큰을 가져옵니다.
     *
     * @param req HTTP 요청 객체
     * @return JWT 토큰 값, 없으면 null
     * @since 2024-10-18
     */
    @Override
    public String getToken(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JwtUtil.AUTHORIZATION_HEADER)) {
                    return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8); // Encode 되어 넘어간 Value 다시 Decode
                }
            }
        }
        return null;
    }

    /**
     * HTTP 요청에서 JWT 토큰을 추출하고, 토큰의 클레임을 가져옵니다.
     *
     * @param req HTTP 요청 객체
     * @return JWT 토큰의 클레임 정보
     * @since 2024-10-31
     */
    @Override
    public Claims getClaims(HttpServletRequest req) {
        String token = getToken(req);
        return this.getClaims(token); // JWT 토큰 substring
    }

    /**
     * HTTP 요청에서 인증된 사용자 정보를 가져옵니다.
     *
     * @param req HTTP 요청 객체
     * @return 인증된 사용자 정보
     * @since 2024-10-31
     */
    @Override
    public User getUser(HttpServletRequest req) {
        return (User) req.getAttribute("user");
    }
}
