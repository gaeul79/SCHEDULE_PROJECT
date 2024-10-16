package com.sparta.schedule_project.dto.response.schedule;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 서버로부터 일정 정보를 받을 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */

@Data
@NoArgsConstructor
public class ScheduleResponseDto {
    private List<ScheduleDto> schedules;
    private PageDto page;
    private ResponseStatusDto status;

    public static ScheduleResponseDto createResponseDto(Page<Schedule> schedules, ResponseCode responseCode) {
        ScheduleResponseDto responseScheduleDto = new ScheduleResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode);
        responseScheduleDto.setPage(new PageDto(schedules.getPageable(), schedules.getTotalPages()));
        responseScheduleDto.setSchedules(schedules.stream().map(ScheduleDto::from).toList());
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }

    public static ScheduleResponseDto createResponseDto(ResponseCode responseCode, String errorMsg) {
        ScheduleResponseDto responseScheduleDto = new ScheduleResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(responseCode, errorMsg);
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }
}
