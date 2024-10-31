package com.sparta.schedule_project.exception;

import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.emums.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseStatusDto(ResponseCode.BAD_INPUT, req, errorMsg));
    }

    /**
     * ResponseException 예외 처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(ResponseException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(ex.getResponseCode().getHttpStatus())
                .body(new ResponseStatusDto(ex.getResponseCode(), req));
    }

    /**
     * 인코딩 관련 예외 처리
     *
     * @since 2024-10-29
     */
    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<ResponseStatusDto> BaseException(UnsupportedEncodingException ex, HttpServletRequest req) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(ResponseCode.TOKEN_FAIL_ENCODING.getHttpStatus())
                .body(new ResponseStatusDto(ResponseCode.TOKEN_FAIL_ENCODING, req));
    }

    /**
     * 그 외 기타 예외처리
     *
     * @since 2024-10-22
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStatusDto> BaseException(Exception ex, HttpServletRequest req) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(ResponseCode.UNKNOWN_ERROR.getHttpStatus())
                .body(new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, req));
    }
}