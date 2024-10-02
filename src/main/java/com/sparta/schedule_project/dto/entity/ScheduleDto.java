package com.sparta.schedule_project.dto.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {
    private String id;
    private String userId;
    private String title;
    private String content;
    private LocalDate createDate;
    private LocalDate updateDate;
}
