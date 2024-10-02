package com.sparta.schedule_project.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ScheduleRequestDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;

    private int startRowNum;
    private int endRowNum;
}
