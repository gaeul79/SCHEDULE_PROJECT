package com.sparta.schedule_project.dto.response.user;

import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.entity.User;
import com.sparta.schedule_project.exception.ResponseCode;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 서버로부터 사용자 정보를 받을 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */

@Data
@NoArgsConstructor
public class UserResponseDto {
    private UserDto user;
    private ResponseStatusDto status;

    public static UserResponseDto createResponseDto(User user, ResponseCode responseCode) {
        UserResponseDto responseDto = new UserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode);
        responseDto.setUser(UserDto.from(user));
        responseDto.setStatus(status);
        return responseDto;
    }

    public static UserResponseDto createResponseDto(ResponseCode responseCode, String errorMsg) {
        UserResponseDto responseDto = new UserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode, errorMsg);
        responseDto.setStatus(status);
        return responseDto;
    }
}
