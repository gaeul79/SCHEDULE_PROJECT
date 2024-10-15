package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.dto.request.schedule.CreateScheduleRequestDto;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보를 요청할 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
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

    public static User to(CreateUserRequestDto userDto) {
        return User.builder()
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .name(userDto.getName()).build();
    }
}
