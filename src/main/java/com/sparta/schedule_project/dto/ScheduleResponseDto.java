package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import com.sparta.schedule_project.dto.entity.UserDto;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class ScheduleResponseDto {
    private List<ScheduleViewDto> schedule;
    private StatusDto statusDto;

    public static ScheduleResponseDto from(List<ScheduleViewDto> schedules, StatusDto statusDto) {
        ScheduleResponseDto responseDto = new ScheduleResponseDto();
        responseDto.setSchedule(schedules);
        responseDto.setStatusDto(statusDto);
        return responseDto;
    }
}
