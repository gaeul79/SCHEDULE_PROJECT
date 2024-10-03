package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.dto.entity.UserDto;
import lombok.Data;

@Data
public class UserResponseDto {
    private UserDto user;
    private ResponseStatusDto responseStatusDto;

    public static UserResponseDto from(UserDto user, ResponseCode responseCode) {
        UserResponseDto responseDto = new UserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode);
        responseDto.setUser(user);
        responseDto.setResponseStatusDto(status);
        return responseDto;
    }

    public static UserResponseDto from(UserDto user, ResponseCode responseCode, String message) {
        UserResponseDto responseDto = new UserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode, message);
        responseDto.setUser(user);
        responseDto.setResponseStatusDto(status);
        return responseDto;
    }
}
