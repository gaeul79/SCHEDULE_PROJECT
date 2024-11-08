package com.sparta.schedule_project.exception;

import com.sparta.schedule_project.emums.ErrorCode;
import lombok.Getter;

/**
 * API 응답 시 발생하는 예외를 나타내는 class
 *
 * @since 2024-10-03
 */
@Getter
public class BusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    /**
     * ResponseException 클래스의 생성자
     * ResponseCode 객체를 기반으로 예외를 생성합니다.
     *
     * @param errorCode 응답 코드 정보를 담은 객체
     * @since 2024-11-05
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    /**
     * ResponseException 클래스의 생성자
     * ResponseCode 객체와 상세 메시지를 기반으로 예외를 생성합니다.
     *
     * @param errorCode 응답 코드 정보를 담은 객체
     * @param detailMsg 상세 메시지
     * @since 2024-11-05
     */
    public BusinessException(ErrorCode errorCode, String detailMsg) {
        super(errorCode.getMessage() + " [" + detailMsg + "]");
        this.errorCode = errorCode;
    }
}
