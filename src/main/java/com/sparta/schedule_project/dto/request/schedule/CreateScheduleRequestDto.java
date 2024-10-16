package com.sparta.schedule_project.dto.request.schedule;

import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 정보를 요청할 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */

@Getter
@NoArgsConstructor
public class CreateScheduleRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    @Max(value = 50, message = "제목은 50자 이상 입력할 수 없습니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Max(value = 300, message = "내용은 300자 이상 입력할 수 없습니다.")
    private String content;

    public static Schedule convertDtoToEntity(CreateScheduleRequestDto scheduleDto, int userSeq, String weather) {
        // TODO. khj 날씨 수정 필요
        return Schedule.builder()
                .user(User.builder().seq(userSeq).build())
                .title(scheduleDto.getTitle())
                .content(scheduleDto.getContent())
                .weather(weather).build();
    }
}
