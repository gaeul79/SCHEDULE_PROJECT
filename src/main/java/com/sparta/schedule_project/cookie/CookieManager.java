package com.sparta.schedule_project.cookie;

import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 공통으로 사용할 수 있는 함수들을 모은 클래스
 *
 * @author 김현정 (수정 필요)
 * @since 2024-10-03
 */
@Service
@RequiredArgsConstructor
public class CookieManager {
    private final JwtUtil jwtUtil;

    /**
     * JWT 토큰을 생성하여 쿠키에 추가하는 메소드입니다.
     *
     * @param res  HTTP 응답 객체
     * @param user 사용자 정보
     */
    public void addJwtToCookie(HttpServletResponse res, User user) {
        String token = jwtUtil.createToken(user.getEmail(), user.getAuth());
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");

        res.addCookie(cookie); // Response 객체에 Cookie 추가
    }

    /**
     * JWT 토큰을 생성하여 헤더에 추가하는 메소드입니다.
     *
     * @param res  HTTP 응답 객체
     * @param user 사용자 정보
     */
    public void addJwtToHeader(HttpServletResponse res, User user) {
        String token = jwtUtil.createToken(user.getEmail(), user.getAuth());
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
    }

    /**
     * JWT 토큰의 사용자 정보가 본인이 맞는지 검증합니다.
     *
     * @param req       HttpServletRequest 객체
     * @param matchUser 검증할 사용자 정보
     * @throws ResponseException 토큰이 유효하지 않거나, 사용자가 존재하지 않거나, 권한이 부족한 경우 예외 발생
     * @since 2024-10-17
     */
    public static void matchUserFromCookie(HttpServletRequest req, User matchUser) throws ResponseException {
        User loginUser = (User) req.getAttribute("user");
        if (!loginUser.getEmail().equals(matchUser.getEmail()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }

    /**
     * 사용자 정보를 추출합니다.
     *
     * @param req HttpServletRequest 객체
     * @return 사용자 정보 객체 (User)
     * @since 2024-10-18
     */
    public static User getUserFromToken(HttpServletRequest req) {
        return (User) req.getAttribute("user");
    }
}
