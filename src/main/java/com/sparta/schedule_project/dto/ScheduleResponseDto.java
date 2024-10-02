package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import lombok.Getter;

import java.util.List;

@Getter
public class ScheduleResponseDto {
    private List<ScheduleViewDto> schedule;
    private StatusDto statusDto;
}
