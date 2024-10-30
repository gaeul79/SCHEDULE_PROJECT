package com.sparta.schedule_project.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule_project.cookie.JwtTokenManager;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;
    private final JwtTokenManager jwtTokenManager;

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
        try {
            String url = request.getRequestURI();
            if (ignorePage(url)) {
                log.info("인증 처리를 하지 않는 URL : {}", url);
            } else {
                String tokenValue = jwtTokenManager.getToken(request);
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
        } catch (ResponseException ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ex.getResponseCode()));
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
        // 회원 가입, 로그인 관련 API 는 인증 필요 없이 요청 진행
        if (!StringUtils.hasText(url))
            return true;
        return url.contains("/api/login") ||
                url.contains("/api/signup") ||
                url.startsWith("/css") ||
                url.startsWith("/js");
    }

    /**
     * JWT 토큰에서 사용자 정보를 추출합니다.
     *
     * @param tokenValue JWT 토큰 값
     * @return 토큰에서 추출된 사용자 정보, 없으면 null
     * @throws IllegalArgumentException 토큰이 없는 경우 발생
     * @since 2024-10-18
     */
    private User getUserInfoFromToken(String tokenValue) throws ResponseException {
        String email = jwtTokenManager.getSubject(tokenValue);
        return userRepository.findByEmail(email);
    }
}