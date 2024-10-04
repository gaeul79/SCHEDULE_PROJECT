package com.sparta.schedule_project.dto.entity;

import com.sparta.schedule_project.dto.ScheduleRequestDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 일정 상세 조회 정보를 담는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleViewDto extends ScheduleDto {
    private String name;

    public ScheduleViewDto(ScheduleRequestDto requestDto) {
        super(requestDto);
        this.name = requestDto.getName();
    }
}
