package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.request.user.CreateUserRequestDto;
import com.sparta.schedule_project.dto.request.user.ModifyUserRequestDto;
import com.sparta.schedule_project.dto.request.user.RemoveUserRequestDto;
import com.sparta.schedule_project.dto.request.user.SearchUserRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.dto.response.user.UserResponseDto;
import com.sparta.schedule_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseStatusDto> login(@RequestBody SearchUserRequestDto createUserRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.login(createUserRequestDto));
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
        return ResponseEntity.status(HttpStatus.OK).body(userService.logout());
    }

    /**
     * 회원가입 API
     *
     * @param createUserRequestDto 회원가입 정보 (JSON 형태)
     * @return 회원가입 처리 결과
     * @author 김현정
     * @since 2023-10-03
     */
    @PostMapping("/users")
    public ResponseEntity<ResponseStatusDto> createUser(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserRequestDto));
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
    public ResponseEntity<UserResponseDto> getUserInfo(@RequestBody SearchUserRequestDto createUserRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.searchUser(createUserRequestDto));
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
    public ResponseEntity<ResponseStatusDto> updateUser(@RequestBody ModifyUserRequestDto createUserRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(createUserRequestDto));
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
    public ResponseEntity<ResponseStatusDto> deleteUser(@RequestBody RemoveUserRequestDto createUserRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(createUserRequestDto));
    }
}
