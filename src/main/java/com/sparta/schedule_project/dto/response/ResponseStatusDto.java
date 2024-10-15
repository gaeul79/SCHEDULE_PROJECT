package com.sparta.schedule_project.dto.response;

import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;

/**
 * API응답 상태에 대한 정보를 제공하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
public class ResponseStatusDto {
    private int state;
    private String message;

    public ResponseStatusDto(ResponseCode responseCode) {
        state = responseCode.getHttpStatus().value();
        message = responseCode.getMessage();
    }

    public ResponseStatusDto(ResponseCode responseCode, String message) {
        state = responseCode.getHttpStatus().value();
        this.message = responseCode.getMessage() + ": " + message;
    }
}
