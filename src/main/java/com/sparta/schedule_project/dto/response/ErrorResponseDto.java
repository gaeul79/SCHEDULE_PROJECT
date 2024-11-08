package com.sparta.schedule_project.dto.response;

import com.sparta.schedule_project.emums.ErrorCode;
import com.sparta.schedule_project.exception.BusinessException;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * API응답 상태에 대한 정보를 제공하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
public class ErrorResponseDto {
    private String date;
    private int state;
    private String message;
    private String url;

    public ErrorResponseDto(BusinessException ex, String requestUrl) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = ex.getErrorCode().getHttpStatus().value();
        message = ex.getMessage();
        url = requestUrl;
    }

    public ErrorResponseDto(ErrorCode errorCode, String requestUrl) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = errorCode.getHttpStatus().value();
        message = errorCode.getMessage();
        url = requestUrl;
    }

    public ErrorResponseDto(ErrorCode errorCode, String requestUrl, String detailMsg) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = errorCode.getHttpStatus().value();
        message = errorCode.getMessage() + ": " + detailMsg;
        url = requestUrl;
    }
}
