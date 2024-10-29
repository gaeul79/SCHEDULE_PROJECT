package com.sparta.schedule_project.dto.response.user;

import com.sparta.schedule_project.cookie.AuthType;
import com.sparta.schedule_project.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보를 담는 응답 DTO 클래스입니다.
 * 서버에서 클라이언트에게 일정 정보를 전달할 때 사용됩니다.
 *
 * @since 2024-10-18
 */
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

    /**
     * User 엔티티 객체를 UserDto 객체로 변환합니다.
     *
     * @param user 변환할 User 엔티티 객체
     * @return 변환된 UserDto 객체
     * @since 2024-10-18
     */
    public static UserDto from(User user) {
        return UserDto.builder().seq(user.getSeq())
                .seq(user.getSeq())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .auth(user.getAuth()).build();
    }
}
