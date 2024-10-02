package com.sparta.schedule_project.dto.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDto {
    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDate createDate;
    private LocalDate updateDate;
}
