package com.sparta.schedule_project.exception;

import lombok.Getter;

/**
 * API 응답 시 발생하는 예외를 나타내는 class
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Getter
public class ResponseException extends Exception {
    private final String result;
    private final ResponseCode responseCode;
    private final String message;

    public ResponseException(ResponseCode responseCode) {
        this.result = "ERROR";
        this.responseCode = responseCode;
        this.message = responseCode.getMessage();
    }

    public ResponseException(ResponseCode responseCode, String message) {
        this.result = "ERROR";
        this.responseCode = responseCode;
        this.message = message;
    }
}
