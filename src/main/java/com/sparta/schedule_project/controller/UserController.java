package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.user.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.user.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.request.user.RemoveUserRequestDto;
import com.sparta.schedule_project.dto.request.user.SearchUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.user.UserResponseDto;
import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.jwt.JwtUtil;
import com.sparta.schedule_project.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * 회원 관리 컨트롤러 클래스입니다.
 *
 * @author 김현정 (수정 필요)
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
     * @param createUserRequestDto 로그인 정보 (JSON 형태)
     * @return 로그인 처리 결과
     * @author 김현정
     * @since 2023-10-03
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseStatusDto> login(HttpServletResponse res, @RequestBody SearchUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.login(res, createUserRequestDto));
        } catch (UnsupportedEncodingException ex) {
            return ResponseEntity
                    .status(ResponseCode.FAIL_ENCODING.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.FAIL_ENCODING, ex.getMessage()));
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
     * @author 김현정
     * @since 2023-10-03
     */
    @PostMapping("/logout")
    public ResponseEntity<ResponseStatusDto> logout() {
        try {
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
     * @author 김현정
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
     * @param createUserRequestDto 회원 정보 조회 정보 (JSON 형태)
     * @return 회원 정보 조회 결과
     * @author 김현정
     * @since 2023-10-03
     */
    @GetMapping("/users")
    public ResponseEntity<UserResponseDto> getUserInfo(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String token, @RequestBody SearchUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.searchUser(token, createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(UserResponseDto.createResponseDto(ex.getResponseCode(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(UserResponseDto.createResponseDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 회원 정보 수정 API
     *
     * @param createUserRequestDto 회원 정보 수정 정보 (JSON 형태)
     * @return 회원 정보 수정 결과
     * @author 김현정
     * @since 2023-10-03
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<ResponseStatusDto> updateUser(@CookieValue(required = false, name = JwtUtil.AUTHORIZATION_HEADER) String tokenValue, @RequestBody ModifyUserRequestDto createUserRequestDto) {
        System.out.println("tokenValue = " + tokenValue);
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.updateUser(tokenValue, createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }

    /**
     * 회원 정보 삭제 API
     *
     * @param createUserRequestDto 회원 정보 삭제 정보 (JSON 형태)
     * @return 회원 정보 삭제 결과
     * @author 김현정
     * @since 2023-10-03
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ResponseStatusDto> deleteUser(@CookieValue(JwtUtil.AUTHORIZATION_HEADER) String token, @RequestBody RemoveUserRequestDto createUserRequestDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userService.deleteUser(token, createUserRequestDto));
        } catch (ResponseException ex) {
            return ResponseEntity
                    .status(ex.getResponseCode().getHttpStatus())
                    .body(new ResponseStatusDto(ex.getResponseCode(), ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                    .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage()));
        }
    }
}
