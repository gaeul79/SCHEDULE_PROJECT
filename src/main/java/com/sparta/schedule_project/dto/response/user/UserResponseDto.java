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

    /**
     * 사용자 정보와 응답 코드를 포함하는 응답 DTO를 생성합니다.
     *
     * @param user         사용자 정보 (UserDto)
     * @param responseCode 응답 코드
     * @return 생성된 응답 DTO 객체 (UserResponseDto)
     * @since 2024-10-18
     */
    public static UserResponseDto createResponseDto(User user, ResponseCode responseCode) {
        UserResponseDto responseDto = new UserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode);
        responseDto.setUser(UserDto.from(user));
        responseDto.setStatus(status);
        return responseDto;
    }

    /**
     * 응답 코드와 에러 메시지를 포함하는 응답 DTO를 생성합니다.
     *
     * @param responseCode 응답 코드
     * @return 생성된 응답 DTO 객체 (UserResponseDto)
     * @since 2024-10-17
     */
    public static UserResponseDto createResponseDto(ResponseCode responseCode) {
        UserResponseDto responseDto = new UserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode);
        responseDto.setStatus(status);
        return responseDto;
    }

    /**
     * 응답 코드와 에러 메시지를 포함하는 응답 DTO를 생성합니다.
     *
     * @param responseCode 응답 코드
     * @param errorMsg     에러 메시지
     * @return 생성된 응답 DTO 객체 (UserResponseDto)
     * @since 2024-10-18
     */
    public static UserResponseDto createResponseDto(ResponseCode responseCode, String errorMsg) {
        UserResponseDto responseDto = new UserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode, errorMsg);
        responseDto.setStatus(status);
        return responseDto;
    }
}
