package com.sparta.schedule_project.dto.entity;

import com.sparta.schedule_project.dto.UserRequestDto;
import lombok.Data;

import java.time.LocalDate;

/**
 * 회원 정보를 담는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
public class UserDto {
    private String userId;
    private String password;
    private String name;
    private String email;
    private LocalDate createDate;
    private LocalDate updateDate;

    public UserDto() {

    }

    /**
     * UserRequestDto 객체로부터 회원 정보를 복사하여 UserDto 객체를 생성합니다.
     *
     * @param user 회원 정보가 담긴 UserRequestDto 객체
     * @return 생성된 UserDto 객체
     * @author 김현정
     * @since 2024-10-03
     */
    public static UserDto from(UserRequestDto user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
