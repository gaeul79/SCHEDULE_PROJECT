package com.sparta.schedule_project.dto.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleViewDto extends ScheduleDto {
    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    private int startRowNum;
    private int endRowNum;
}
