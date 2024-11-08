package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.request.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseDto;
import com.sparta.schedule_project.dto.response.UserDto;
import com.sparta.schedule_project.emums.ErrorCode;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.BusinessException;
import com.sparta.schedule_project.repository.UserRepository;
import com.sparta.schedule_project.util.PasswordEncoder;
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

    /**
     * 회원가입을 처리합니다.
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 회원가입 요청 정보
     * @return 회원가입 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    public ResponseDto<UserDto> createUser(HttpServletRequest req, CreateUserRequestDto requestDto) throws BusinessException {
        validateCreateUserInfo(requestDto);
        User user = requestDto.convertDtoToEntity(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        return ResponseDto.of(UserDto.from(user));
    }

    /**
     * 회원 정보를 조회합니다.
     *
     * @param userId 조회할 회원 번호
     * @return 회원 조회 결과 (UserResponseDto)
     * @since 2024-10-03
     */
    public ResponseDto<UserDto> searchUser(int userId) throws BusinessException {
        User user = findUserById(userId);
        return ResponseDto.of(UserDto.from(user));
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param loginUser  로그인한 유저
     * @param requestDto 수정할 정보
     * @return 회원 정보 수정 결과 (ResponseStatusDto)
     * @since 2024-10-03
     */
    @Transactional
    public ResponseDto<UserDto> updateUser(User loginUser, ModifyUserRequestDto requestDto) throws BusinessException {
        User updateUser = findUserById(requestDto.getUserId());
        isSame(loginUser.getId(), updateUser.getId());
        updateUser.update(requestDto, passwordEncoder.encode(requestDto.getPassword()));
        return ResponseDto.of(UserDto.from(updateUser));
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param loginUser 로그인한 유저
     * @param userId    삭제할 회원 번호
     * @since 2024-10-03
     */
    public void deleteUser(User loginUser, int userId) throws BusinessException {
        User deleteUser = findUserById(userId);
        isSame(loginUser.getId(), deleteUser.getId());
        userRepository.delete(deleteUser);
    }

    /**
     * 멤버 번호로 유저를 조회합니다.
     *
     * @param id 유저 id
     * @return 검색된 회원
     * @throws BusinessException 검색된 유저가 없을시 발생하는 예외
     * @since 2024-10-23
     */
    public User findUserById(int id) throws BusinessException {
        User user = userRepository.findById(id);
        if (user == null)
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        return user;
    }

    /**
     * 두 사용자 ID가 같은지 확인합니다.
     *
     * @param loginUserId 로그인한 사용자의 ID
     * @param findUserId  찾고자 하는 사용자의 ID
     * @throws BusinessException 두 사용자 ID가 다를 경우 발생하는 예외
     */
    public void isSame(int loginUserId, int findUserId) throws BusinessException {
        if (loginUserId != findUserId)
            throw new BusinessException(ErrorCode.INVALID_PERMISSION);
    }

    /**
     * 사용자 생성 시 올바른 정보를 입력하였는지 검사합니다.
     *
     * @param requestDto 생성하려는 사용자 정보
     * @throws BusinessException 아이디가 중복될 경우 발생
     * @since 2024-10-07
     */
    public void validateCreateUserInfo(CreateUserRequestDto requestDto) throws BusinessException {
        User user = userRepository.findByEmail(requestDto.getEmail());
        if (user != null) // 중복 이메일 확인
            throw new BusinessException(ErrorCode.USER_EMAIL_DUPLICATED);
    }
}
