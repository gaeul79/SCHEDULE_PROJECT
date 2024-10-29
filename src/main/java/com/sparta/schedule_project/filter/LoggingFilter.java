package com.sparta.schedule_project.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 요청 URI를 로깅하는 필터입니다.
 *
 * @since 2024-10-17
 */
@Order(1) // 순서..
@Slf4j(topic = "LoggingFilter")
@Component
public class LoggingFilter implements Filter {
    /**
     * 필터 실행 메서드입니다. 요청과 응답 객체, 필터 체인을 이용하여 요청 처리를 수행합니다.
     *
     * @param request  요청 객체
     * @param response 응답 객체
     * @param chain    필터 체인
     * @throws IOException      입출력 관련 예외
     * @throws ServletException 서블릿 관련 예외
     * @since 2024-10-18
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 전처리
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String url = httpServletRequest.getRequestURI();
        log.info(url);

        chain.doFilter(request, response); // 다음 Filter 로 이동

        // 후처리
        log.info(url + " 비즈니스 로직 완료");
    }
}