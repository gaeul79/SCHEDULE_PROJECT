package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.response.*;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.BusinessException;
import com.sparta.schedule_project.service.UserService;
import com.sparta.schedule_project.util.login.LoginUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 관리 컨트롤러 클래스입니다.
 *
 * @since 2024-10-03
 */
@RestController
@RequiredArgsConstructor // UserService 객체를 의존성 주입 방식으로 받아오는 코드 생략 가능
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    /**
     * 회원가입 API
     *
     * @param req                  HttpServletRequest 객체
     * @param createUserRequestDto 회원가입 정보 (JSON 형태)
     * @return 회원가입 처리 결과
     * @since 2023-10-03
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<UserDto>> createUser(
            HttpServletRequest req,
            @RequestBody @Valid CreateUserRequestDto createUserRequestDto) throws BusinessException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(req, createUserRequestDto));
    }

    /**
     * 회원 정보 조회 API
     *
     * @param userId 조회할 회원 번호
     * @return 회원 정보 조회 결과
     * @since 2023-10-03
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> getUserInfo(
            @PathVariable int userId) throws BusinessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.searchUser(userId));
    }

    /**
     * 회원 정보 수정 API
     *
     * @param user       로그인 유저
     * @param requestDto 회원 정보 수정 정보 (JSON 형태)
     * @return 회원 정보 수정 결과
     * @since 2023-10-03
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseDto<UserDto>> updateUser(
            @LoginUser User user,
            @RequestBody @Valid ModifyUserRequestDto requestDto) throws BusinessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(user, requestDto));
    }

    /**
     * 회원 정보 삭제 API
     *
     * @param user       로그인 유저
     * @param userId 삭제할 회원 번호
     * @since 2023-10-03
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(
            @LoginUser User user,
            @PathVariable int userId) throws BusinessException {
        userService.deleteUser(user, userId);
        return ResponseEntity.noContent().build();
    }
}
