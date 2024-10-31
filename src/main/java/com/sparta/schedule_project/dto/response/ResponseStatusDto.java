package com.sparta.schedule_project.dto.response;

import com.sparta.schedule_project.emums.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
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
public class ResponseStatusDto {
    private String date;
    private int state;
    private String message;
    private String url;

    public ResponseStatusDto(ResponseCode responseCode, HttpServletRequest req) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = responseCode.getHttpStatus().value();
        message = responseCode.getMessage();
        url = req.getRequestURL().toString();
    }

    public ResponseStatusDto(ResponseCode responseCode, HttpServletRequest req, String message) {
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        state = responseCode.getHttpStatus().value();
        this.message = responseCode.getMessage() + ": " + message;
        url = req.getRequestURL().toString();
    }
}
