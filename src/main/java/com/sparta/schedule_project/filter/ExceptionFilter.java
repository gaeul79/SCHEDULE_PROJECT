package com.sparta.schedule_project.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 예외 처리 필터
 *
 * @since 2024-10-31
 */
@Order(2)
@Slf4j(topic = "ExceptionFilter")
@RequiredArgsConstructor
@Component
public class ExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

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
            filterChain.doFilter(request, response); // 다음 Filter 로 이동
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(ResponseCode.TOKEN_UNSIGNED.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.TOKEN_UNSIGNED, request));
            response.getWriter().write(json);
        } catch (ExpiredJwtException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(ResponseCode.TOKEN_TIMEOUT.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.TOKEN_TIMEOUT, request));
            response.getWriter().write(json);
        } catch (UnsupportedJwtException e) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(ResponseCode.TOKEN_UNSUPPORTED.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.TOKEN_UNSUPPORTED, request));
            response.getWriter().write(json);
        } catch (ResponseException ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(ex.getResponseCode().getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ex.getResponseCode(), request));
            response.getWriter().write(json);
        } catch (Exception ex) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(ResponseCode.UNKNOWN_ERROR.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, request));
            response.getWriter().write(json);
        }
    }
}
