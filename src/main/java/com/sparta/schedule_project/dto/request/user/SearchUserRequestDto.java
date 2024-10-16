package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자 정보를 요청할 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */

@Data
@NoArgsConstructor
public class SearchUserRequestDto {
    private int userSeq;
    private String email;
    private String password;

    public static User convertDtoToEntity(SearchUserRequestDto userDto) {
        return User.builder()
                .seq(userDto.getUserSeq())
                .email(userDto.getEmail())
                .password(userDto.getPassword()).build();
    }
}
