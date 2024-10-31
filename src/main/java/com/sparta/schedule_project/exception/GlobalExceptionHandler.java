package com.sparta.schedule_project.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.emums.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.UnsupportedEncodingException;

/**
 * 예외 처리 클래스.
 *
 * @since 2024-10-21
 */
@Slf4j(topic = "exception:")
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 입력 관련 예외 처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        // 에러 메시지 추출
        String errorMsg = ex.getBindingResult().
                getAllErrors()
                .get(0)
                .getDefaultMessage();

        return validException(req, errorMsg);
    }

    /**
     * JSON 관련 예외 처리
     *
     * @since 2024-10-31
     */
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(JsonProcessingException ex, HttpServletRequest req) {
        log.error(ex.getMessage());
        return baseException(req, ResponseCode.JSON_INVALID);
    }

    /**
     * ResponseException 예외 처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(ResponseException ex, HttpServletRequest req) {
        return baseException(req, ex.getResponseCode());
    }

    /**
     * 인코딩 관련 예외 처리
     *
     * @since 2024-10-29
     */
    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(UnsupportedEncodingException ex, HttpServletRequest req) {
        log.error(ex.getMessage());
        return baseException(req, ResponseCode.TOKEN_FAIL_ENCODING);
    }

    /**
     * 그 외 기타 예외처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStatusDto> BaseException(Exception ex, HttpServletRequest req) {
        log.error(ex.getMessage());
        return baseException(req, ResponseCode.UNKNOWN_ERROR);
    }

    /**
     * 기본적인 예외 처리를 위한 메서드입니다.
     *
     * @param req          HTTP 요청 객체
     * @param responseCode 응답 코드
     * @return ResponseEntity 객체
     * @since 2024-10-24
     */
    private ResponseEntity<ResponseStatusDto> baseException(HttpServletRequest req, ResponseCode responseCode) {
        String url = req.getRequestURL().toString();
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(new ResponseStatusDto(responseCode, url));
    }

    /**
     * 유효성 검사 실패 시 발생하는 예외 처리를 위한 메서드입니다.
     *
     * @param req      HTTP 요청 객체
     * @param errorMsg 에러 메시지
     * @return ResponseEntity 객체
     * @since 2024-10-24
     */
    private ResponseEntity<ResponseStatusDto> validException(HttpServletRequest req, String errorMsg) {
        String url = req.getRequestURL().toString();
        return ResponseEntity
                .status(ResponseCode.BAD_INPUT.getHttpStatus())
                .body(new ResponseStatusDto(ResponseCode.BAD_INPUT, url, errorMsg));
    }
}