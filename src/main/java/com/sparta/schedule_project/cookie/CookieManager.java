package com.sparta.schedule_project.cookie;

import com.sparta.schedule_project.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class CookieManager {
    private final JwtUtil jwtUtil;

    // 생성된 JWT를 Cookie에 저장
    public void addJwtToCookie(User user, HttpServletResponse res) throws UnsupportedEncodingException {
        String token = jwtUtil.createToken(user.getEmail(), user.getAuth());
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");

        // Response 객체에 Cookie 추가
        res.addCookie(cookie);
    }

    public User getUserFromJwtToken(String tokenValue) {
        // JWT 토큰 substring
        String token = jwtUtil.substringToken(tokenValue);

        // 토큰 검증
        if(!jwtUtil.validateToken(token)){
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 사용자 정보 가져오기
        Claims info = jwtUtil.getUserInfoFromToken(token);
        // 사용자 email
        String email = info.getSubject();
        // 사용자 권한
        AuthType auth = AuthType.valueOf(info.get(JwtUtil.AUTHORIZATION_KEY).toString());

        return User.builder()
                .email(email)
                .auth(auth).build();
    }
}
