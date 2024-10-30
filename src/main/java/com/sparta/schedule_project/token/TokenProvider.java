package com.sparta.schedule_project.token;

import com.sparta.schedule_project.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * TokenProvider 인터페이스
 * 로그인 종류마다 상속받아 오버라이딩하여 사용
 *
 * @since 2024-10-31
 */
public interface TokenProvider {
    void setToken(HttpServletResponse res, User user);

    String getToken(HttpServletRequest req);

    void matchToken(HttpServletRequest req, User user);

    Claims getClaims(HttpServletRequest req);

    User getUser(HttpServletRequest req);
}
