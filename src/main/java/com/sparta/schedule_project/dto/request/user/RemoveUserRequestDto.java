package com.sparta.schedule_project.dto.request.user;

import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 유저 삭제 요청 DTO 클래스
 *
 * @since 2024-10-03
 */
@Getter
@NoArgsConstructor
public class RemoveUserRequestDto {
    @Positive(message = "유저번호는 0이상의 숫자입니다.")
    private int userSeq;

    /**
     * DTO 객체를 User 엔티티 객체로 변환합니다.
     *
     * @param userDto 회원가입 요청 DTO
     * @return 생성된 User 엔티티 객체
     * @since 2024-10-18
     */
    public User convertDtoToEntity(RemoveUserRequestDto userDto) {
        return User.builder()
                .seq(userDto.getUserSeq()).build();
    }
}
