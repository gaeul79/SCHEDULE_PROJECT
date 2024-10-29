package com.sparta.schedule_project.dto.request;

import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.cookie.AuthType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원가입 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class CreateUserRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-z]+$",
            message = "올바른 형식의 이메일을 입력해주세요")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이입니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Max(value = 20, message = "닉네임은 20자까지 입력할 수 있습니다.")
    private String name;

    @NotBlank(message = "권한은 공백일 수 없습니다.")
    private AuthType auth;

    /**
     * DTO 객체를 User 엔티티 객체로 변환합니다.
     *
     * @param password 암호화된 비밀번호
     * @return 생성된 User 엔티티 객체
     * @since 2024-10-18
     */
    public User convertDtoToEntity(String password) {
        return User.builder()
                .password(password)
                .email(email)
                .name(name)
                .auth(auth).build();
    }
}
