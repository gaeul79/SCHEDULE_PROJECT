package com.sparta.schedule_project.dto.entity;

import com.sparta.schedule_project.dto.UserRequestDto;
import lombok.Data;

import java.time.LocalDate;

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

    public static UserDto from(UserRequestDto user) {
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
