package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;

@Data
public class ResponseStatusDto {
    private int state;
    private String message;

    public ResponseStatusDto(ResponseCode responseCode) {
        state = responseCode.getStatus().value();
        message = responseCode.getMessage();
    }

    public ResponseStatusDto(ResponseCode responseCode, String message) {
        state = responseCode.getStatus().value();
        this.message = responseCode.getMessage() + message;
    }
}
