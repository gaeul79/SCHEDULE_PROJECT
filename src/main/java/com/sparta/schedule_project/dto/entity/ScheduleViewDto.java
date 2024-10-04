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

    /**
     * ScheduleRequestDto 객체로부터 일정 정보를 복사하여 ScheduleViewDto 객체를 생성
     *
     * @param requestDto 일정 정보가 담긴 ScheduleRequestDto 객체
     * @return 생성된 ScheduleViewDto 객체
     * @author 김현정
     * @since 2024-10-03
     */
    public static ScheduleViewDto from(ScheduleRequestDto requestDto) {
        return new ScheduleViewDto(requestDto);
    }
}
