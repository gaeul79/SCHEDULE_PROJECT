package com.sparta.schedule_project.service;

import com.sparta.schedule_project.cookie.CookieManager;
import com.sparta.schedule_project.dto.request.user.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.user.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.request.user.RemoveUserRequestDto;
import com.sparta.schedule_project.dto.request.user.SearchUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.user.UserResponseDto;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

/**
 * 사용자 관리 서비스 클래스
 * UserRepository 이용하여 사용자를 관리합니다.
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CookieManager cookieManager;

    /**
     * 로그인을 처리합니다.
     *
     * @param requestDto 로그인 요청 정보
     * @return 로그인 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto login(HttpServletResponse res, SearchUserRequestDto requestDto) throws ResponseException, UnsupportedEncodingException {
        User user = requestDto.convertDtoToEntity(requestDto);
        User findUser = userRepository.findByEmail(user.getEmail());
        checkLoginUserInputParam(user, findUser);
        cookieManager.addJwtToCookie(findUser, res);
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN);
    }

    /**
     * 로그인 시 유저 정보를 검사합니다.
     *
     * @param inputUser  입력된 유저 정보
     * @param findUser 조회된 유저 정보
     * @throws ResponseException 유저 정보가 올바르지 않은 경우 예외를 발생시킵니다.
     * @author 김현정
     * @since 2024-10-03
     */
    public void checkLoginUserInputParam(User inputUser, User findUser) throws ResponseException {
        if (findUser == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);
        else if (!passwordEncoder.matches(inputUser.getPassword(), findUser.getPassword()))
            throw new ResponseException(ResponseCode.USER_PASSWORD_NOT_MATCH);
    }

    /**
     * 로그아웃을 처리합니다.
     *
     * @return 로그아웃 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto logout() {
        // TODO. 토큰 해제
        return new ResponseStatusDto(ResponseCode.SUCCESS_LOGOUT);
    }

    /**
     * 회원가입을 처리합니다.
     *
     * @param requestDto 회원가입 요청 정보
     * @return 회원가입 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto createUser(CreateUserRequestDto requestDto) throws ResponseException {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = requestDto.convertDtoToEntity(requestDto);
        checkCreateUser(user);
        userRepository.save(user);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
    }

    /**
     * 사용자 생성 시 올바른 정보를 입력하였는지 검사합니다.
     *
     * @param user 생성하려는 사용자 정보
     * @throws ResponseException 아이디가 중복될 경우 발생
     */
    public void checkCreateUser(User user) throws ResponseException {
        User findUser = userRepository.findByEmail(user.getEmail());
        if (findUser != null) // 중복 이메일 확인
            throw new ResponseException(ResponseCode.USER_EMAIL_DUPLICATED);
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param requestDto 회원 조회 요청 정보
     * @return 회원 조회 결과 (UserResponseDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public UserResponseDto searchUser(String token, SearchUserRequestDto requestDto) throws ResponseException {
        User user = requestDto.convertDtoToEntity(requestDto);
        User findUser = userRepository.findBySeq(user.getSeq());
        checkUser(token, findUser);
        return UserResponseDto.createResponseDto(findUser, ResponseCode.SUCCESS_SEARCH_USER);
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param requestDto 회원 정보 수정 요청 정보
     * @return 회원 정보 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @Transactional
    public ResponseStatusDto updateUser(String token, ModifyUserRequestDto requestDto) throws ResponseException {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        User user = requestDto.convertDtoToEntity(requestDto);
        checkUser(token, user);
        User updateUser = userRepository.findBySeq(user.getSeq());
        updateUser.update(user);
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_USER);
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param requestDto 회원 삭제 요청 정보
     * @return 회원 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    @Transactional
    public ResponseStatusDto deleteUser(String token, RemoveUserRequestDto requestDto) throws ResponseException {
        User user = requestDto.convertDtoToEntity(requestDto);
        checkUser(token, user);
        userRepository.delete(user);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_USER);
    }

    private void checkUser(String token, User user) throws ResponseException {
        User loginUser = cookieManager.getUserFromJwtToken(token);
        User searchUser = userRepository.findBySeq(user.getSeq());
        if(searchUser == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);

        if (!loginUser.getEmail().equals(searchUser.getEmail()))
            throw new ResponseException(ResponseCode.INVALID_PERMISSION);
    }
}
