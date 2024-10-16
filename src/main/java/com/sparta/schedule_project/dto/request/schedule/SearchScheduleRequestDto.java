package com.sparta.schedule_project.dto.request.schedule;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 정보를 요청할 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */

@Getter
@NoArgsConstructor
public class SearchScheduleRequestDto extends PageDto {
    public Schedule convertDtoToEntity(SearchScheduleRequestDto scheduleDto) {
        return Schedule.builder()
                .page(scheduleDto.convertDtoToPageable()).build();
    }
}
