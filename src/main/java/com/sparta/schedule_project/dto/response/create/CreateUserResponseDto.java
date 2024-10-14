package com.sparta.schedule_project.dto.response.create;

import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;

/**
 * 서버로부터 사용자 정보를 받을 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
public class CreateUserResponseDto {
    private User user;
    private ResponseStatusDto responseStatusDto;

    public static CreateUserResponseDto from(User user, ResponseCode responseCode) {
        CreateUserResponseDto responseDto = new CreateUserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode);
        responseDto.setUser(user);
        responseDto.setResponseStatusDto(status);
        return responseDto;
    }

    public static CreateUserResponseDto from(User user, ResponseCode responseCode, String message) {
        CreateUserResponseDto responseDto = new CreateUserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode, message);
        responseDto.setUser(user);
        responseDto.setResponseStatusDto(status);
        return responseDto;
    }
}
