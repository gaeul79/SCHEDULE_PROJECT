package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 수정 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Data
@NoArgsConstructor
public class ModifyUserRequestDto {
    @Positive(message = "유저번호는 0이상의 숫자입니다.")
    private int userSeq;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8~20자 사이입니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Max(value = 20, message = "닉네임은 20자까지 입력할 수 있습니다.")
    private String name;

    /**
     * DTO 객체를 User 엔티티 객체로 변환합니다.
     *
     * @param userDto 회원가입 요청 DTO
     * @return 생성된 User 엔티티 객체
     * @since 2024-10-18
     */
    public User convertDtoToEntity(ModifyUserRequestDto userDto) {
        return User.builder()
                .seq(userDto.getUserSeq())
                .password(userDto.getPassword())
                .name(userDto.getName()).build();
    }
}
