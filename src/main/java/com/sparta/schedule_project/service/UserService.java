package com.sparta.schedule_project.service;

import com.sparta.schedule_project.config.PasswordEncoder;
import com.sparta.schedule_project.cookie.JwtTokenManager;
import com.sparta.schedule_project.dto.request.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.UserResponseDto;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관리 서비스 클래스
 * UserRepository 이용하여 사용자를 관리합니다.
 *
 * @since 2024-10-03
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager tokenManager;

    /**
     * 회원가입을 처리합니다.
     *
     * @param requestDto 회원가입 요청 정보
     * @return 회원가입 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseStatusDto createUser(CreateUserRequestDto requestDto) throws ResponseException {
        validateCreateUserInfo(requestDto);
        User user = requestDto.convertDtoToEntity(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
    }

    /**
     * 사용자 생성 시 올바른 정보를 입력하였는지 검사합니다.
     *
     * @param requestDto 생성하려는 사용자 정보
     * @throws ResponseException 아이디가 중복될 경우 발생
     * @since 2024-10-07
     */
    public void validateCreateUserInfo(CreateUserRequestDto requestDto) throws ResponseException {
        User user = userRepository.findByEmail(requestDto.getEmail());
        if (user != null) // 중복 이메일 확인
            throw new ResponseException(ResponseCode.USER_EMAIL_DUPLICATED);
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param userId 조회할 회원 번호
     * @return 회원 조회 결과 (UserResponseDto)
     * @since 2024-10-03
     */
    public UserResponseDto searchUser(int userId) throws ResponseException {
        User user = findUserById(userId);
        return UserResponseDto.createResponseDto(user, ResponseCode.SUCCESS_SEARCH_USER);
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 수정할 정보
     * @return 회원 정보 수정 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @Transactional
    public ResponseStatusDto updateUser(HttpServletRequest req, ModifyUserRequestDto requestDto) throws ResponseException {
        User user = findUserById(requestDto.getUserId());
        tokenManager.matchUserFromToken(req, user);
        user.update(requestDto, passwordEncoder.encode(requestDto.getPassword()));
        return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_USER);
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param req    HttpServletRequest 객체
     * @param userId 삭제할 회원 번호
     * @return 회원 삭제 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @Transactional
    public ResponseStatusDto deleteUser(HttpServletRequest req, int userId) throws ResponseException {
        User deleteUser = findUserById(userId);
        tokenManager.matchUserFromToken(req, deleteUser);
        userRepository.delete(deleteUser);
        return new ResponseStatusDto(ResponseCode.SUCCESS_DELETE_USER);
    }

    /**
     * 멤버 번호로 유저를 조회합니다.
     *
     * @param id 유저 id
     * @return 검색된 회원
     * @throws ResponseException 검색된 유저가 없을시 발생하는 예외
     * @since 2024-10-23
     */
    public User findUserById(int id) throws ResponseException {
        User user = userRepository.findById(id);
        if (user == null)
            throw new ResponseException(ResponseCode.USER_NOT_FOUND);
        return user;
    }
}
