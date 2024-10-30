package com.sparta.schedule_project.token;

import com.sparta.schedule_project.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TokenProvider {
    void setToken(HttpServletResponse res, User user);
    String getToken(HttpServletRequest req);
    void matchToken(HttpServletRequest req, User user);
    Claims getClaims(HttpServletRequest req);
    User getUser(HttpServletRequest req);
}
