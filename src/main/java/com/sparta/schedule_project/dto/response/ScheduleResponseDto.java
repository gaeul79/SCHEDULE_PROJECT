package com.sparta.schedule_project.dto.response;

import com.sparta.schedule_project.emums.ResponseCode;
import com.sparta.schedule_project.entity.Schedule;
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

    /**
     * 일정 목록 조회 결과를 기반으로 응답 DTO를 생성합니다.
     * 조회된 일정 목록, 페이징 정보, 응답 코드를 포함합니다.
     *
     * @param requestUrl 요청 url
     * @param schedules  조회된 일정 목록 (Page<Schedule>)
     * @return 생성된 응답 DTO 객체 (ScheduleResponseDto)
     * @since 2024-10-18
     */
    public static ScheduleResponseDto createResponseDto(String requestUrl, Page<Schedule> schedules) {
        ScheduleResponseDto responseScheduleDto = new ScheduleResponseDto();
        ResponseStatusDto responseStatusDto = new ResponseStatusDto(ResponseCode.SUCCESS_SEARCH_SCHEDULE, requestUrl);
        responseScheduleDto.setPage(new PageDto(schedules.getPageable(), schedules.getTotalPages()));
        responseScheduleDto.setSchedules(schedules.stream().map(ScheduleDto::from).toList());
        responseScheduleDto.setStatus(responseStatusDto);
        return responseScheduleDto;
    }
}
