package com.sparta.schedule_project.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleRequestDto {
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
