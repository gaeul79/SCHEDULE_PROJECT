package com.sparta.schedule_project.dto.request.schedule;

import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
public class RemoveScheduleRequestDto {
    @Positive(message = "일정번호는 0이상 숫자입니다.")
    private int scheduleSeq;

    @Positive(message = "유저번호는 0이상 숫자입니다.")
    private int userSeq;

    public Schedule convertDtoToEntity(RemoveScheduleRequestDto scheduleDto) {
        return Schedule.builder()
                .seq(scheduleDto.getScheduleSeq())
                .user(User.builder().seq(scheduleDto.getUserSeq()).build()).build();
    }
}
