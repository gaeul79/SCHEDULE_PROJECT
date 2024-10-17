package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.user.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.user.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.request.user.RemoveUserRequestDto;
import com.sparta.schedule_project.dto.request.user.SearchUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.user.UserResponseDto;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
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
    public ResponseEntity<ResponseStatusDto> login(HttpServletResponse res, @RequestBody SearchUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.login(res, createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode()));
        } catch (UnsupportedEncodingException ex) {
            return ResponseEntity
                    .status(ResponseCode.TOKEN_FAIL_ENCODING.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.TOKEN_FAIL_ENCODING));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 로그아웃 API
     *
     * @return 로그아웃 처리 결과
     * @since 2023-10-03
     */
    @PostMapping("/logout")
    public ResponseEntity<ResponseStatusDto> logout() {
        try {
            // TODO. khj 쿠키 해제 필요
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.logout());
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 회원가입 API
     *
     * @param createUserRequestDto 회원가입 정보 (JSON 형태)
     * @return 회원가입 처리 결과
     * @since 2023-10-03
     */
    @PostMapping("/signup")
    public ResponseEntity<ResponseStatusDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.createUser(createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 회원 정보 조회 API
     *
     * @param req                  HttpServletRequest
     * @param createUserRequestDto 회원 정보 조회 정보 (JSON 형태)
     * @return 회원 정보 조회 결과
     * @since 2023-10-03
     */
    public ResponseEntity<UserResponseDto> getUserInfo(HttpServletRequest req, @RequestBody SearchUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.searchUser(req, createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(UserResponseDto.createResponseDto(ex.getResponseCode()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(UserResponseDto.createResponseDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 회원 정보 수정 API
     *
     * @param req                  HttpServletRequest 객체
     * @param createUserRequestDto 회원 정보 수정 정보 (JSON 형태)
     * @return 회원 정보 수정 결과
     * @since 2023-10-03
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseStatusDto> updateUser(HttpServletRequest req, @RequestBody ModifyUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.updateUser(req, createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 회원 정보 삭제 API
     *
     * @param req                  HttpServletRequest 객체
     * @param createUserRequestDto 회원 정보 삭제 정보 (JSON 형태)
     * @return 회원 정보 삭제 결과
     * @since 2023-10-03
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseStatusDto> deleteUser(HttpServletRequest req, @RequestBody RemoveUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.deleteUser(req, createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }
}
