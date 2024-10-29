package com.sparta.schedule_project.dto.request;

import com.sparta.schedule_project.common.entity.User;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 생성 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {
    @Positive(message = "일정번호는 0이상 숫자입니다.")
    private int scheduleId;

    @NotBlank(message = "댓글내용을 입력해주세요.")
    @Max(value = 300, message = "댓글은 300자 이상 입력할 수 없습니다.")
    private String content;

    /**
     * DTO 객체를 엔티티 객체로 변환
     *
     * @param user 사용자 정보
     * @return 생성된 댓글 엔티티 객체
     * @since 2024-10-18
     */
    public Comment convertDtoToEntity(User user) {
        return Comment.builder()
                .schedule(Schedule.builder().id(scheduleId).build())
                .user(user)
                .content(content).build();
    }
}
