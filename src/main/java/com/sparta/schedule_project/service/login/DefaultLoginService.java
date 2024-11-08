package com.sparta.schedule_project.service.login;

import com.sparta.schedule_project.dto.request.LoginRequestDto;
import com.sparta.schedule_project.dto.response.ResponseDto;
import com.sparta.schedule_project.dto.response.UserDto;
import com.sparta.schedule_project.emums.ErrorCode;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.BusinessException;
import com.sparta.schedule_project.repository.UserRepository;
import com.sparta.schedule_project.util.PasswordEncoder;
import com.sparta.schedule_project.util.login.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 기본 로그인 기능을 제공하는 서비스
 *
 * @since 2024-10-31
 */
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
     * @return 로그인 결과
     * @since 2024-10-03
     */
    public ResponseDto<UserDto> login(HttpServletRequest req, HttpServletResponse res, LoginRequestDto requestDto) throws BusinessException {
        User user = userRepository.findByEmail(requestDto.getEmail());
        validateLoginInfo(requestDto, user);
        tokenProvider.setToken(res, user);
        return ResponseDto.of(UserDto.from(user));
    }

    /**
     * 로그인 시 유저 정보를 검사합니다.
     *
     * @param requestDto 입력된 유저 정보
     * @param findUser   조회된 유저 정보
     * @throws BusinessException 유저 정보가 올바르지 않은 경우 예외를 발생시킵니다.
     * @since 2024-10-03
     */
    public void validateLoginInfo(LoginRequestDto requestDto, User findUser) throws BusinessException {
        if (findUser == null)
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        else if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword()))
            throw new BusinessException(ErrorCode.USER_PASSWORD_NOT_MATCH);
    }
}
