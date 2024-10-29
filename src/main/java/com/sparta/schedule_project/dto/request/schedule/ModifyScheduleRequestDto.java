package com.sparta.schedule_project.dto.request.schedule;

import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.common.entity.User;
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

    /**
     * DTO 객체를 Schedule 엔티티 객체로 변환
     *
     * @param weather     날씨 정보
     * @return 생성된 Schedule 엔티티 객체
     * @since 2024-10-18
     */
    public Schedule convertDtoToEntity(String weather) {
        return Schedule.builder()
                .seq(scheduleSeq)
                .user(User.builder().seq(userSeq).build())
                .title(title)
                .weather(weather)
                .content(content).build();
    }
}
