package com.sparta.schedule_project.dto.response.user;

import com.sparta.schedule_project.jwt.AuthType;
import com.sparta.schedule_project.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private int seq;
    private String email;
    private String password;
    private String name;
    private AuthType auth;

    public static UserDto from(User user) {
        return UserDto.builder().seq(user.getSeq())
                .seq(user.getSeq())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .auth(user.getAuth()).build();
    }
}
