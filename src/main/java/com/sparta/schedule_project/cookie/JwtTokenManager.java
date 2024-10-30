package com.sparta.schedule_project.cookie;

import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class JwtTokenManager {
    public final JwtUtil jwtUtil;

    public abstract void addToken(HttpServletResponse res, User user);

    public abstract String getToken(HttpServletRequest req);

    /**
     * JWT 토큰의 사용자 정보가 본인이 맞는지 검증합니다.
     *
     * @param req  HttpServletRequest 객체
     * @param user 검증할 사용자 정보
     * @throws ResponseException 토큰이 유효하지 않거나, 사용자가 존재하지 않거나, 권한이 부족한 경우 예외 발생
     * @since 2024-10-17
     */
    public static void matchUserFromToken(HttpServletRequest req, User user) throws ResponseException {
        User loginUser = (User) req.getAttribute("user");
        if (!loginUser.getEmail().equals(user.getEmail()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }

    /**
     * 사용자 정보를 추출합니다.
     *
     * @param req HttpServletRequest 객체
     * @return 사용자 정보 객체 (User)
     * @since 2024-10-18
     */
    public static User getUser(HttpServletRequest req) {
        return (User) req.getAttribute("user");
    }

    public String getSubject(String token) {
        return jwtUtil.getSubject(token);
    }
}
