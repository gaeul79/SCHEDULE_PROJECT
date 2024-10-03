package com.sparta.schedule_project.dto.entity;

import com.sparta.schedule_project.dto.ScheduleRequestDto;
import lombok.Data;

@Data
public class ScheduleViewDto extends ScheduleDto {
    private String name;

    public static ScheduleViewDto from(ScheduleRequestDto requestDto) {
        ScheduleViewDto scheduleDto = new ScheduleViewDto();
        scheduleDto.setId(requestDto.getId());
        scheduleDto.setUserId(requestDto.getUserId());
        scheduleDto.setName(requestDto.getName());
        scheduleDto.setTitle(requestDto.getTitle());
        scheduleDto.setContent(requestDto.getContent());
        scheduleDto.setStartUpdateDate(requestDto.getStartUpdateDate());
        scheduleDto.setEndUpdateDate(requestDto.getEndUpdateDate());
        scheduleDto.setPage(requestDto.getPage());
        scheduleDto.setSize(requestDto.getSize());
        return scheduleDto;
    }
}
