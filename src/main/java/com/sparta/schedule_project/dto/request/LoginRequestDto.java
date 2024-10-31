package com.sparta.schedule_project.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
