package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.ResponseStatusDto;
import com.sparta.schedule_project.dto.UserRequestDto;
import com.sparta.schedule_project.dto.UserResponseDto;
import com.sparta.schedule_project.dto.entity.UserDto;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * 사용자 관리 서비스 클래스
 * UserRepository 이용하여 사용자를 관리합니다.
 *
 * @author 김현정 (수정 필요)
 * @since 2024-10-03
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * UserRepository 객체를 의존성 주입 방식으로 받아옵니다.
     *
     * @param userRepository 사용자 레포지토리
     * @author 김현정
     * @since 2024-10-03
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 로그인을 처리합니다.
     *
     * @param requestUserDto 로그인 요청 정보
     * @return 로그인 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto login(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            UserDto resultUser = userRepository.findById(user);
            checkLoginUser(user, resultUser);
            return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN);
        } catch (ResponseException ex) {
            return new ResponseStatusDto(ex.getResponseCode());
        }
    }

    /**
     * 로그인 시 유저 정보를 검사합니다.
     *
     * @param inputUser  입력된 유저 정보
     * @param resultUser 조회된 유저 정보
     * @throws ResponseException 유저 정보가 올바르지 않은 경우 예외를 발생시킵니다.
     * @author 김현정
     * @since 2024-10-03
     */
    public void checkLoginUser(UserDto inputUser, UserDto resultUser) throws ResponseException {
        if (resultUser == null)
            throw new ResponseException(ResponseCode.USER_NAME_NOT_FOUND);
        else if (!inputUser.getPassword().equals(resultUser.getPassword()))
            throw new ResponseException(ResponseCode.USER_PASSWORD_NOT_FOUND);
    }

    /**
     * 로그아웃을 처리합니다.
     *
     * @return 로그아웃 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto logout() {
        try {
            return new ResponseStatusDto(ResponseCode.SUCCESS_LOGOUT);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 회원가입을 처리합니다.
     *
     * @param userRequestDto 회원가입 요청 정보
     * @return 회원가입 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto createUser(UserRequestDto userRequestDto) {
        try {
            UserDto user = UserDto.from(userRequestDto);
            checkCreateUser(user);
            userRepository.createUser(user);
            return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
        } catch (ResponseException ex) {
            return new ResponseStatusDto(ex.getResponseCode());
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR);
        }
    }

    /**
     * 사용자 생성 시 올바른 정보를 입력하였는지 검사합니다.
     *
     * @param user 생성하려는 사용자 정보
     * @throws ResponseException 아이디가 중복될 경우 발생
     */
    public void checkCreateUser(UserDto user) throws ResponseException {
        UserDto resultUser = userRepository.findById(user);
        if (resultUser != null) // 중복 아이디인지 확인
            throw new ResponseException(ResponseCode.USER_NAME_DUPLICATED);
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param userRequestDto 회원 조회 요청 정보
     * @return 회원 조회 결과 (UserResponseDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public UserResponseDto searchUser(UserRequestDto userRequestDto) {
        try {
            UserDto user = UserDto.from(userRequestDto);
            UserDto resultUser = userRepository.searchUser(user);
            return UserResponseDto.from(resultUser, ResponseCode.SUCCESS_SEARCH_USER);
        } catch (Exception ex) {
            return UserResponseDto.from(null, ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param userRequestDto 회원 정보 수정 요청 정보
     * @return 회원 정보 수정 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto updateUser(UserRequestDto userRequestDto) {
        try {
            UserDto user = UserDto.from(userRequestDto);
            userRepository.updateUser(user);
            return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_USER);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param userRequestDto 회원 삭제 요청 정보
     * @return 회원 삭제 결과 (ResponseStatusDto)
     * @author 김현정
     * @since 2024-10-03
     */
    public ResponseStatusDto deleteUser(UserRequestDto userRequestDto) {
        try {
            UserDto user = UserDto.from(userRequestDto);
            userRepository.deleteUser(user);
            return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
