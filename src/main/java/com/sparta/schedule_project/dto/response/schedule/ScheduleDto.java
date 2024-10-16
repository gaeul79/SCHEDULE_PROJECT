package com.sparta.schedule_project.dto.response.schedule;

import com.sparta.schedule_project.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 일정 정보를 담는 응답 DTO 클래스입니다.
 * 서버에서 클라이언트에게 일정 정보를 전달할 때 사용됩니다.
 *
 * @since 2024-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private int seq;
    private int userSeq;
    private String userName;
    private String title;
    private String content;
    private String weather;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    /**
     * Schedule 엔티티 객체를 ScheduleDto 객체로 변환합니다.
     *
     * @param schedule 변환할 Schedule 엔티티 객체
     * @return 변환된 ScheduleDto 객체
     * @since 2024-10-18
     */
    public static ScheduleDto from(Schedule schedule) {
        return ScheduleDto.builder().seq(schedule.getSeq())
                .userSeq(schedule.getUser().getSeq())
                .userName(schedule.getUser().getName())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .weather(schedule.getWeather())
                .createDate(schedule.getCreateDate())
                .updateDate(schedule.getUpdateDate()).build();
    }
}
