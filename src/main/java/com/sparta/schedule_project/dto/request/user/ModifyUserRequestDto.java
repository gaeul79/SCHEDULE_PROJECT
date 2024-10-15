package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
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
public class ModifyUserRequestDto {
    @NotBlank(message = "유저번호는 공백일 수 없습니다.")
    private int userSeq;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이입니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Max(value = 20, message = "닉네임은 20자까지 입력할 수 있습니다.")
    private String name;

    public static User to(ModifyUserRequestDto userDto) {
        return User.builder()
                .seq(userDto.getUserSeq())
                .password(userDto.getPassword())
                .name(userDto.getName()).build();
    }
}
