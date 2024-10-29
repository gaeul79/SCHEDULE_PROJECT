package com.sparta.schedule_project.dto.request;

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
}
