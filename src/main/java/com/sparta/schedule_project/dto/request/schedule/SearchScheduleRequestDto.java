package com.sparta.schedule_project.dto.request.schedule;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.entity.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 일정 조회 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class SearchScheduleRequestDto extends PageDto {
    /**
     * DTO 객체를 Schedule 엔티티 객체로 변환
     *
     * @param scheduleDto 일정 생성 요청 DTO
     * @return 생성된 Schedule 엔티티 객체
     * @since 2024-10-18
     */
    public Schedule convertDtoToEntity(SearchScheduleRequestDto scheduleDto) {
        return Schedule.builder()
                .page(scheduleDto.convertDtoToPageable()).build();
    }
}
