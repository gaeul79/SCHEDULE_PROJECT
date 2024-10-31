package com.sparta.schedule_project.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.schedule_project.dto.request.LoginRequestDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.emums.SocialType;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.service.login.DefaultLoginService;
import com.sparta.schedule_project.service.login.SocialLoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 로그인 컨트롤러
 *
 * @since 2024-10-03
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {
    private final DefaultLoginService defaultLoginService;
    private final SocialLoginService socialLoginService;

    /**
     * 로그인 API
     *
     * @param req        HttpServletRequest 객체
     * @param res        HttpServletResponse 객체
     * @param requestDto 로그인 정보 (JSON 형태)
     * @return 로그인 처리 결과
     * @since 2023-10-03
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseStatusDto> login(
            HttpServletRequest req,
            HttpServletResponse res,
            @RequestBody @Valid LoginRequestDto requestDto) throws ResponseException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(defaultLoginService.login(req, res, requestDto));
    }

    @GetMapping("/{socialType}/login/callback")
    public ResponseEntity<ResponseStatusDto> socialLogin(
            HttpServletRequest req,
            HttpServletResponse res,
            @RequestParam String code,
            @PathVariable SocialType socialType) throws ResponseException, JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(socialLoginService.get(socialType).login(req, res, code));
    }
}
