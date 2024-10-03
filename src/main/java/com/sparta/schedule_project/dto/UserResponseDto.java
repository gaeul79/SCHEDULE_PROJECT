package com.sparta.schedule_project.dto;

import com.sparta.schedule_project.dto.entity.UserDto;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;

/**
 * 서버로부터 사용자 정보를 받을 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
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
