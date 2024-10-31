package com.sparta.schedule_project.service.login;

import com.sparta.schedule_project.config.PasswordEncoder;
import com.sparta.schedule_project.dto.request.LoginRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.UserRepository;
import com.sparta.schedule_project.token.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultLoginService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    /**
     * 로그인을 처리합니다.
     *
     * @param req        HttpServletRequest 객체
     * @param res        HttpServletResponse 객체
     * @param requestDto 로그인 요청 정보
     * @return 로그인 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseStatusDto login(HttpServletRequest req, HttpServletResponse res, LoginRequestDto requestDto) throws ResponseException {
        User user = userRepository.findByEmail(requestDto.getEmail());
        validateLoginInfo(requestDto, user);
        tokenProvider.setToken(res, user);
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN, req.getRequestURI());
    }

    /**
     * 로그인 시 유저 정보를 검사합니다.
     *
     * @param requestDto 입력된 유저 정보
     * @param findUser   조회된 유저 정보
     * @throws ResponseException 유저 정보가 올바르지 않은 경우 예외를 발생시킵니다.
     * @since 2024-10-03
     */
    public void validateLoginInfo(LoginRequestDto requestDto, User findUser) throws ResponseException {
        if (findUser == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);
        else if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword()))
            throw new ResponseException(ResponseCode.USER_PASSWORD_NOT_MATCH);
    }
}