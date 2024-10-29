package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.LoginRequestDto;
import com.sparta.schedule_project.dto.request.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.UserResponseDto;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.service.LoginService;
import com.sparta.schedule_project.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * 회원 관리 컨트롤러 클래스입니다.
 *
 * @since 2024-10-03
 */
@RestController
@RequiredArgsConstructor // UserService 객체를 의존성 주입 방식으로 받아오는 코드 생략 가능
@RequestMapping("/api.sparta.com")
public class UserController {
    private final LoginService loginService;
    private final UserService userService;

    /**
     * 로그인 API
     *
     * @param res                  HttpServletResponse 객체
     * @param createUserRequestDto 로그인 정보 (JSON 형태)
     * @return 로그인 처리 결과
     * @since 2023-10-03
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseStatusDto> login(
            HttpServletResponse res,
            @RequestBody LoginRequestDto createUserRequestDto) throws ResponseException, UnsupportedEncodingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginService.login(res, createUserRequestDto));
    }

    /**
     * 로그아웃 API
     *
     * @return 로그아웃 처리 결과
     * @since 2023-10-03
     */
    @PostMapping("/logout")
    public ResponseEntity<ResponseStatusDto> logout() {
        // TODO. khj 쿠키 해제 필요
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loginService.logout());
    }

    /**
     * 회원가입 API
     *
     * @param createUserRequestDto 회원가입 정보 (JSON 형태)
     * @return 회원가입 처리 결과
     * @since 2023-10-03
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseStatusDto> createUser(
            @RequestBody CreateUserRequestDto createUserRequestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(createUserRequestDto));
    }

    /**
     * 회원 정보 조회 API
     *
     * @param userSeq 조회할 회원 번호
     * @return 회원 정보 조회 결과
     * @since 2023-10-03
     */
    @PostMapping("/users/{userSeq}")
    public ResponseEntity<UserResponseDto> getUserInfo(
            @PathVariable int userSeq) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.searchUser(userSeq));
    }

    /**
     * 회원 정보 수정 API
     *
     * @param req                  HttpServletRequest 객체
     * @param createUserRequestDto 회원 정보 수정 정보 (JSON 형태)
     * @return 회원 정보 수정 결과
     * @since 2023-10-03
     */
    @PutMapping("/users/{userSeq}")
    public ResponseEntity<ResponseStatusDto> updateUser(
            HttpServletRequest req,
            @RequestBody ModifyUserRequestDto createUserRequestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.updateUser(req, createUserRequestDto));
    }

    /**
     * 회원 정보 삭제 API
     *
     * @param req     HttpServletRequest 객체
     * @param userSeq 삭제할 회원 번호
     * @return 회원 정보 삭제 결과
     * @since 2023-10-03
     */
    @DeleteMapping("/users/{userSeq}")
    public ResponseEntity<ResponseStatusDto> deleteUser(
            HttpServletRequest req,
            @PathVariable int userSeq) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteUser(req, userSeq));
    }
}
