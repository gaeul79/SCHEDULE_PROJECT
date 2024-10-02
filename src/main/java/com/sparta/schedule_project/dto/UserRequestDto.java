package com.sparta.schedule_project.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String userId;
    private String password;
    private String name;
    private String email;
}
