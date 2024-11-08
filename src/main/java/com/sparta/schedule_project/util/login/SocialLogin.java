package com.sparta.schedule_project.util.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.schedule_project.dto.response.ResponseDto;
import com.sparta.schedule_project.dto.response.UserDto;
import com.sparta.schedule_project.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 소셜 로그인 서비스의 공통적인 기능을 정의한 인터페이스
 *
 * @since 2024-10-31
 */
public interface SocialLogin {
    /**
     * 소셜 로그인을 처리하는 메서드입니다.
     *
     * @param req        HTTP 요청 객체
     * @param res        HTTP 응답 객체
     * @param accessCode 소셜 로그인 서비스에서 제공하는 접근 코드
     * @return 로그인 성공 여부와 메시지를 담은 ResponseStatusDto
     * @throws JsonProcessingException JSON 처리 중 발생하는 예외
     * @since 2024-10-31
     */
    ResponseDto<UserDto> login(HttpServletRequest req, HttpServletResponse res, String accessCode) throws JsonProcessingException;

    /**
     * 소셜 로그인 서비스에서 액세스 토큰을 발급받는 메서드입니다.
     *
     * @param accessCode 소셜 로그인 서비스에서 제공하는 접근 코드
     * @return 발급받은 액세스 토큰
     * @throws JsonProcessingException JSON 처리 중 발생하는 예외
     * @since 2024-10-31
     */
    String getToken(String accessCode) throws JsonProcessingException;

    /**
     * 소셜 로그인 서비스에서 사용자 정보를 조회하는 메서드입니다.
     *
     * @param accessToken 발급받은 액세스 토큰
     * @return 조회된 사용자 정보
     * @throws JsonProcessingException JSON 처리 중 발생하는 예외
     * @since 2024-10-31
     */
    User getUser(String accessToken) throws JsonProcessingException;

    /**
     * 새로운 사용자를 가입시키는 메서드입니다.
     *
     * @param user 가입할 사용자 정보
     * @since 2024-10-31
     */
    void signUp(User user);
}
