package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;

import java.util.List;

/**
 * 서버로부터 일정 정보를 받을 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
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
