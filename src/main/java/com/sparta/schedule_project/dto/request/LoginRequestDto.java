package com.sparta.schedule_project.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 정보 조회 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
