package com.sparta.schedule_project.util.token;

import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * JWT(JSON Web Token)를 이용하여 헤더에 사용자 정보를 저장하고 인증을 처리하는 클래스입니다.
 *
 * @since 2024-10-31
 */
@RequiredArgsConstructor
@Component
public class JwtHeaderTokenProvider extends JwtUtil implements TokenProvider {
    /**
     * JWT 토큰을 생성하여 헤더에 추가하는 메소드입니다.
     *
     * @param res  HTTP 응답 객체
     * @param user 사용자 정보
     */
    @Override
    public void setToken(HttpServletResponse res, User user) {
        String token = this.createToken(user.getEmail(), user.getAuth());
        token = URLEncoder
                .encode(token, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행
        res.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
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
        String token = req.getHeader(JwtUtil.AUTHORIZATION_HEADER);
        return URLDecoder.decode(token, StandardCharsets.UTF_8); // Encode 되어 넘어간 Value 다시 Decode
    }

    /**
     * JWT 토큰의 사용자 정보가 본인이 맞는지 검증합니다.
     *
     * @param req  HttpServletRequest 객체
     * @param user 검증할 사용자 정보
     * @throws ResponseException 토큰이 유효하지 않거나, 사용자가 존재하지 않거나, 권한이 부족한 경우 예외 발생
     * @since 2024-10-17
     */
    @Override
    public void matchToken(HttpServletRequest req, User user) {
        User loginUser = (User) req.getAttribute("user");
        if (!loginUser.getEmail().equals(user.getEmail()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
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
