package com.sparta.schedule_project.entity;

import com.sparta.schedule_project.dto.request.create.CreateScheduleRequestDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 일정 정보를 담는 Entity 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Setter
    private String id;
    private String userId;
    private String title;
    private String content;
    private LocalDate createDate;
    private LocalDate updateDate;

    private LocalDate startUpdateDate;
    private LocalDate endUpdateDate;

    private Integer page;
    private Integer size;

    public Schedule(CreateScheduleRequestDto requestDto) {
        this.id = requestDto.getId();
        this.userId = requestDto.getUserId();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.startUpdateDate = requestDto.getStartUpdateDate();
        this.endUpdateDate = requestDto.getEndUpdateDate();
    }
}
