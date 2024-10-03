package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleResponseDto {
    private List<ScheduleViewDto> schedule;
    private ResponseStatusDto responseStatusDto;

    public static ScheduleResponseDto from(List<ScheduleViewDto> schedules, ResponseCode responseCode) {
        ScheduleResponseDto responseScheduleDto = new ScheduleResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode);
        responseScheduleDto.setSchedule(schedules);
        responseScheduleDto.setResponseStatusDto(responseStatusDto);
        return responseScheduleDto;
    }

    public static ScheduleResponseDto from(List<ScheduleViewDto> schedules, ResponseCode responseCode, String message) {
        ScheduleResponseDto responseScheduleDto = new ScheduleResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode, message);
        responseScheduleDto.setSchedule(schedules);
        responseScheduleDto.setResponseStatusDto(responseStatusDto);
        return responseScheduleDto;
    }
}
