package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 정보 조회 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class SearchUserRequestDto {
    private int userSeq;
    private String email;
    private String password;

    /**
     * DTO 객체를 User 엔티티 객체로 변환합니다.
     *
     * @param userDto 회원가입 요청 DTO
     * @return 생성된 User 엔티티 객체
     * @since 2024-10-18
     */
    public User convertDtoToEntity(SearchUserRequestDto userDto) {
        return User.builder()
                .seq(userDto.getUserSeq())
                .email(userDto.getEmail())
                .password(userDto.getPassword()).build();
    }
}
