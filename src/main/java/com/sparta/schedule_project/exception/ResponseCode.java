package com.sparta.schedule_project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * API 응답 시 사용되는 상태 코드와 메시지를 정의하는 enum
 *
 * @since 2024-10-03
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS_LOGIN(HttpStatus.OK, "로그인 성공"),
    SUCCESS_LOGOUT(HttpStatus.OK, "로그아웃 성공"),

    SUCCESS_CREATE_USER(HttpStatus.OK, "회원 가입 성공"),
    SUCCESS_UPDATE_USER(HttpStatus.OK, "유저 수정 성공"),
    SUCCESS_SEARCH_USER(HttpStatus.OK, "유저 조회 성공"),
    SUCCESS_DELETE_USER(HttpStatus.OK, "유저 삭제 성공"),

    SUCCESS_CREATE_SCHEDULE(HttpStatus.OK, "일정 등록 성공"),
    SUCCESS_UPDATE_SCHEDULE(HttpStatus.OK, "일정 수정 성공"),
    SUCCESS_DELETE_SCHEDULE(HttpStatus.OK, "일정 삭제 성공"),
    SUCCESS_SEARCH_SCHEDULE(HttpStatus.OK, "일정 조회 성공"),

    SUCCESS_CREATE_COMMENT(HttpStatus.OK, "댓글 등록 성공"),
    SUCCESS_UPDATE_COMMENT(HttpStatus.OK, "댓글 수정 성공"),
    SUCCESS_DELETE_COMMENT(HttpStatus.OK, "댓글 삭제 성공"),
    SUCCESS_SEARCH_COMMENT(HttpStatus.OK, "댓글 조회 성공"),

    // DB & 서버 관련 에러 코드
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB 에러가 발생하였습니다"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류"),

    // 사용자 관련 에러 코드
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    USER_PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 틀렸습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이메일이 중복됩니다"),

    // 토큰 관련 에러 코드
    TOKEN_FAIL_ENCODING(HttpStatus.BAD_REQUEST, "잘못된 인코딩을 사용하였습니다."),
    TOKEN_UNSIGNED(HttpStatus.BAD_REQUEST, "유효하지 않는 JWT 서명 입니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "잘못된 JWT 토큰 입니다."),
    TOKEN_TIMEOUT(HttpStatus.UNAUTHORIZED, "만료된 JWT token 입니다."),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰 입니다."),

    // 일정 관련 에러 코드
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "이미 삭제된 일정입니다."),

    // 댓글 관련 에러 코드
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "이미 삭제된 댓글입니다."),

    // 권한 관련 에러 코드
    INVALID_PERMISSION(HttpStatus.FORBIDDEN, "권한이 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}