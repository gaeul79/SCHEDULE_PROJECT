package com.sparta.schedule_project.dto.entity;

import com.sparta.schedule_project.dto.ScheduleRequestDto;
import lombok.Data;

import java.time.LocalDate;

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
