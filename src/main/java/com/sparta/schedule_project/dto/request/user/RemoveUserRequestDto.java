package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.dto.request.schedule.RemoveScheduleRequestDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "유저번호는 공백일 수 없습니다.")
    private int userSeq;

    public static User to(RemoveUserRequestDto userDto) {
        return User.builder()
                .seq(userDto.getUserSeq()).build();
    }
}
