package com.sparta.schedule_project.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.schedule_project.dto.response.ErrorResponseDto;
import com.sparta.schedule_project.emums.ErrorCode;
import com.sparta.schedule_project.exception.BusinessException;
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
     * @param req         요청 객체
     * @param res         응답 객체
     * @param filterChain 필터 체인
     * @throws IOException jwt 관련 에러
     * @since 2024-10-18
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(req, res); // 다음 Filter 로 이동
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException ex) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(ErrorCode.TOKEN_UNSIGNED.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ErrorResponseDto(ErrorCode.TOKEN_UNSIGNED, req.getRequestURI()));
            res.getWriter().write(json);
        } catch (ExpiredJwtException e) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(ErrorCode.TOKEN_TIMEOUT.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ErrorResponseDto(ErrorCode.TOKEN_TIMEOUT, req.getRequestURI()));
            res.getWriter().write(json);
        } catch (UnsupportedJwtException e) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(ErrorCode.TOKEN_UNSUPPORTED.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ErrorResponseDto(ErrorCode.TOKEN_UNSUPPORTED, req.getRequestURI()));
            res.getWriter().write(json);
        } catch (BusinessException ex) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(ex.getErrorCode().getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ErrorResponseDto(ex.getErrorCode(), req.getRequestURI()));
            res.getWriter().write(json);
        } catch (Exception ex) {
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.setStatus(ErrorCode.UNKNOWN_ERROR.getHttpStatus().value());
            String json = objectMapper.writeValueAsString(new ErrorResponseDto(ErrorCode.UNKNOWN_ERROR, req.getRequestURI()));
            res.getWriter().write(json);
        }
    }
}
