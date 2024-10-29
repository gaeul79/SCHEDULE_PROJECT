package com.sparta.schedule_project.dto.request;

import com.sparta.schedule_project.common.entity.User;
import com.sparta.schedule_project.entity.Schedule;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 등록 요청 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-18
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

    /**
     * DTO 객체를 Schedule 엔티티 객체로 변환
     *
     * @param userId  사용자 번호
     * @param weather 날씨 정보
     * @return 생성된 Schedule 엔티티 객체
     * @since 2024-10-18
     */
    public Schedule convertDtoToEntity(int userId, String weather) {
        return Schedule.builder()
                .user(User.builder().id(userId).build())
                .title(title)
                .content(content)
                .weather(weather).build();
    }
}
