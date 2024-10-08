package com.sparta.schedule_project.dto;

import lombok.Getter;

import java.time.LocalDate;

/**
 * 일정 정보를 요청할 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Getter
public class ScheduleRequestDto {
    private String loginUserId;

    private String id;
    private String userId;
    private String name;
    private String title;
    private String content;

    private LocalDate startUpdateDate;
    private LocalDate endUpdateDate;

    private int page;
    private int size;
}
