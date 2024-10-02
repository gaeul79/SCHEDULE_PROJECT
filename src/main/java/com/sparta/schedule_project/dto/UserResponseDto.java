package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.dto.entity.UserDto;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private UserDto user;
    private Status status;
}
