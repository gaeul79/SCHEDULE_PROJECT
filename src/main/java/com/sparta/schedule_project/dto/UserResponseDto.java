package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.dto.entity.UserDto;
import lombok.Data;

@Data
public class UserResponseDto {
    private UserDto user;
    private StatusDto statusDto;

    public static UserResponseDto from(UserDto user, StatusDto statusDto) {
        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setUser(user);
        responseDto.setStatusDto(statusDto);
        return responseDto;
    }
}
