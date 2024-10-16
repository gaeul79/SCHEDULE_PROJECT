package com.sparta.schedule_project.filter;


import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.jwt.JwtUtil;
import com.sparta.schedule_project.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Order(2)
@Slf4j(topic = "AuthFilter")
@RequiredArgsConstructor
public class AuthFilter implements Filter {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();

        if(ignorePage(url)) {
            log.info("인증 처리를 하지 않는 URL : " + url);
        }
        else {
            String tokenValue = getTokenFromRequest(httpServletRequest);
            User user = getUserInfoFromToken(tokenValue);
            request.setAttribute("user", user);
        }

        chain.doFilter(request, response); // 다음 Filter 로 이동
    }

    private boolean ignorePage(String url) {
        // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
        if (!StringUtils.hasText(url))
            return false;
        else if(url.contains("/api/login") ||
                url.contains("/api/signup") ||
                url.startsWith("/css") ||
                url.startsWith("/js"))
            return false;
        else
            return true;
    }

    private User getUserInfoFromToken(String tokenValue) {
        // 토큰 확인
        if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
            // JWT 토큰 substring
            String token = jwtUtil.substringToken(tokenValue);

            // 토큰 검증
            if (!jwtUtil.validateToken(token)) {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 사용자 정보 가져오기
            Claims info = jwtUtil.getUserInfoFromToken(token);
            return userRepository.findBySeq(Integer.parseInt(info.getSubject()));
        } else {
            throw new IllegalArgumentException("Not Found Token");
        }
    }

    private String getTokenFromRequest(HttpServletRequest req) {
        // HttpServletRequest 에서 Cookie Value : JWT 가져오기
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JwtUtil.AUTHORIZATION_HEADER)) {
                    return URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8); // Encode 되어 넘어간 Value 다시 Decode
                }
            }
        }
        return null;
    }
}