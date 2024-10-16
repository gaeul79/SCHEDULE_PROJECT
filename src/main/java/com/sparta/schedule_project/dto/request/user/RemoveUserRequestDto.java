package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class RemoveUserRequestDto {
    @Positive(message = "유저번호는 0이상의 숫자입니다.")
    private int userSeq;

    public static User convertDtoToEntity(RemoveUserRequestDto userDto) {
        return User.builder()
                .seq(userDto.getUserSeq()).build();
    }
}
