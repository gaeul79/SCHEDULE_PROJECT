package com.sparta.schedule_project.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 수정 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class ModifyScheduleRequestDto {
    @Positive(message = "일정번호는 0이상 숫자입니다.")
    private int scheduleSeq;

    @Positive(message = "유저번호는 0이상 숫자입니다.")
    private int userSeq;

    @NotBlank(message = "제목을 입력해주세요.")
    @Max(value = 50, message = "제목은 50자 이상 입력할 수 없습니다.")
    private String title;

    @NotBlank(message = "제목을 입력해주세요.")
    @Max(value = 300, message = "내용은 300자 이상 입력할 수 없습니다.")
    private String content;
}
