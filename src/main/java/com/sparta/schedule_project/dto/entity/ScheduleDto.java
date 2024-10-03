package com.sparta.schedule_project.dto.entity;

import com.sparta.schedule_project.dto.ScheduleRequestDto;
import lombok.Data;

import java.time.LocalDate;

/**
 * 일정 정보를 담는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
public class ScheduleDto {
    private String id;
    private String userId;
    private String title;
    private String content;
    private LocalDate createDate;
    private LocalDate updateDate;

    private LocalDate startUpdateDate;
    private LocalDate endUpdateDate;

    private Integer page;
    private Integer size;

    /**
     * ScheduleRequestDto 객체로부터 일정 정보를 복사하여 ScheduleDto 객체를 생성합니다.
     *
     * @param requestDto 일정 정보가 담긴 ScheduleRequestDto 객체
     * @return 생성된 ScheduleDto 객체
     * @author 김현정
     * @since 2024-10-03
     */
    public static ScheduleDto from(ScheduleRequestDto requestDto) {
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setId(requestDto.getId());
        scheduleDto.setUserId(requestDto.getUserId());
        scheduleDto.setTitle(requestDto.getTitle());
        scheduleDto.setContent(requestDto.getContent());
        scheduleDto.setStartUpdateDate(requestDto.getStartUpdateDate());
        scheduleDto.setEndUpdateDate(requestDto.getEndUpdateDate());
        return scheduleDto;
    }
}
