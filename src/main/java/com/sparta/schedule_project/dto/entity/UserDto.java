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

    public UserDto(String userId, String password, String name, String email, LocalDate createDate, LocalDate updateDate) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static UserDto from(UserRequestDto user) {
        UserDto userDto = new UserDto();
        userDto.userId = user.getUserId();
        userDto.password = user.getPassword();
        userDto.name = user.getName();
        userDto.email = user.getEmail();
        return userDto;
    }
}
