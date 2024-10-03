package com.sparta.schedule_project.exception;

import lombok.Getter;

@Getter
public class ResponseException extends Exception{
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
