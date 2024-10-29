package com.sparta.schedule_project.dto.request.schedule;

import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.common.entity.User;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 삭제 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class RemoveScheduleRequestDto {
    @Positive(message = "일정번호는 0이상 숫자입니다.")
    private int scheduleSeq;

    @Positive(message = "유저번호는 0이상 숫자입니다.")
    private int userSeq;

    /**
     * DTO 객체를 Schedule 엔티티 객체로 변환
     *
     * @return 생성된 Schedule 엔티티 객체
     * @since 2024-10-18
     */
    public Schedule convertDtoToEntity() {
        return Schedule.builder()
                .seq(scheduleSeq)
                .user(User.builder().seq(userSeq).build()).build();
    }
}
