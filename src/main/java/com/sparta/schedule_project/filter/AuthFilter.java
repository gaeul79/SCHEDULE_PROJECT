package com.sparta.schedule_project.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule_project.jwt.JwtUtil;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.common.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.common.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * JWT 기반 인증 처리 필터
 *
 * @since 2024-10-18
 */
@Order(2)
@Slf4j(topic = "AuthFilter")
@RequiredArgsConstructor
@Component
public class AuthFilter extends OncePerRequestFilter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * 요청 URI에 따라 인증 처리 여부를 판단하고,
     * 토큰 검증 후 사용자 정보를 추출하여 다음 필터로 전달합니다.
     *
     * @param request     요청 객체
     * @param response    응답 객체
     * @param filterChain 필터 체인
     * @throws IOException jwt 관련 에러
     * @since 2024-10-18
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        String url = request.getRequestURI();

        try {
            if (ignorePage(url))
                log.info("인증 처리를 하지 않는 URL : {}", url);
            else {
                String tokenValue = getTokenFromRequest(request);
                User user = getUserInfoFromToken(tokenValue);
                request.setAttribute("user", user);
            }
            filterChain.doFilter(request, response); // 다음 Filter 로 이동
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.TOKEN_UNSIGNED));
            response.getWriter().write(json);
        } catch (ExpiredJwtException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.TOKEN_TIMEOUT));
            response.getWriter().write(json);
        } catch (UnsupportedJwtException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.TOKEN_UNSUPPORTED));
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR));
            response.getWriter().write(json);
        }
    }

    /**
     * 주어진 URL이 인증을 필요로 하지 않는 URL인지 판단합니다.
     *
     * @param url 검사할 URL
     * @return 인증이 필요하지 않으면 true, 아니면 false
     * @since 2024-10-18
     */
    private boolean ignorePage(String url) {
        // 회원가입, 로그인 관련 API 는 인증 필요없이 요청 진행
        if (!StringUtils.hasText(url))
            return true;
        else if (url.contains("/api.sparta.com/login") ||
                url.contains("/api.sparta.com/signup") ||
                url.startsWith("/css") ||
                url.startsWith("/js"))
            return true;
        else
            return false;
    }

    /**
     * JWT 토큰에서 사용자 정보를 추출합니다.
     *
     * @param tokenValue JWT 토큰 값
     * @return 토큰에서 추출된 사용자 정보, 없으면 null
     * @throws IllegalArgumentException 토큰이 없는 경우 발생
     * @since 2024-10-18
     */
    private User getUserInfoFromToken(String tokenValue) {
        // 토큰 확인
        if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
            // JWT 토큰 substring
            String token = jwtUtil.substringToken(tokenValue);

            // 토큰 검증
            if (jwtUtil.validateToken(token)) {
                Claims info = jwtUtil.getUserInfoFromToken(token); // 토큰에서 사용자 정보 가져오기
                return userRepository.findByEmail(info.getSubject());
            }

            return null;
        } else {
            throw new IllegalArgumentException("Not Found Token");
        }
    }

    /**
     * HTTP 요청에서 쿠키 값으로 JWT 토큰을 가져옵니다.
     *
     * @param req HTTP 요청 객체
     * @return JWT 토큰 값, 없으면 null
     * @since 2024-10-18
     */
    private String getTokenFromRequest(HttpServletRequest req) {
        // HttpServletRequest 에서 Cookie Value : JWT 가져오기
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
}