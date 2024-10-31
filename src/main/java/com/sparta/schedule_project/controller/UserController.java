package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.UserResponseDto;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.service.UserService;
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
    public ResponseEntity<ResponseStatusDto> createUser(
            HttpServletRequest req,
            @RequestBody @Valid CreateUserRequestDto createUserRequestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(req, createUserRequestDto));
    }

    /**
     * 회원 정보 조회 API
     *
     * @param req    HttpServletRequest 객체
     * @param userId 조회할 회원 번호
     * @return 회원 정보 조회 결과
     * @since 2023-10-03
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(
            HttpServletRequest req,
            @PathVariable int userId) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.searchUser(req, userId));
    }

    /**
     * 회원 정보 수정 API
     *
     * @param req        HttpServletRequest 객체
     * @param requestDto 회원 정보 수정 정보 (JSON 형태)
     * @return 회원 정보 수정 결과
     * @since 2023-10-03
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseStatusDto> updateUser(
            HttpServletRequest req,
            @RequestBody @Valid ModifyUserRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(req, requestDto));
    }

    /**
     * 회원 정보 삭제 API
     *
     * @param req    HttpServletRequest 객체
     * @param userId 삭제할 회원 번호
     * @return 회원 정보 삭제 결과
     * @since 2023-10-03
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseStatusDto> deleteUser(
            HttpServletRequest req,
            @PathVariable int userId) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteUser(req, userId));
    }
}
