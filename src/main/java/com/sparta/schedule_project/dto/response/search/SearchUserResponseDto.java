package com.sparta.schedule_project.dto.response.search;

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
public class SearchUserResponseDto {
    private User user;
    private ResponseStatusDto responseStatusDto;

    public static SearchUserResponseDto from(User user, ResponseCode responseCode) {
        SearchUserResponseDto responseDto = new SearchUserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode);
        responseDto.setUser(user);
        responseDto.setResponseStatusDto(status);
        return responseDto;
    }

    public static SearchUserResponseDto from(User user, ResponseCode responseCode, String message) {
        SearchUserResponseDto responseDto = new SearchUserResponseDto();
        ResponseStatusDto status = new ResponseStatusDto(responseCode, message);
        responseDto.setUser(user);
        responseDto.setResponseStatusDto(status);
        return responseDto;
    }
}
