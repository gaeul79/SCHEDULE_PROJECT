package com.sparta.schedule_project.util.login;

import com.sparta.schedule_project.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * TokenProvider 인터페이스
 *
 * @since 2024-10-31
 */
public interface TokenProvider {
    void setToken(HttpServletResponse res, User user);

    String getToken(HttpServletRequest req);

    Claims getClaims(HttpServletRequest req);

    User getUser(HttpServletRequest req);
}
