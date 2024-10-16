package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 삭제 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class RemoveCommentRequestDto {
    @Positive(message = "댓글번호는 0이상 숫자입니다.")
    private int commentSeq;

    @Positive(message = "유저번호는 0이상 숫자입니다.")
    private int userSeq;

    /**
     * DTO 객체를 엔티티 객체로 변환
     *
     * @param commentDto 요청 DTO 객체
     * @return 생성된 댓글 엔티티 객체
     * @since 2024-10-18
     */
    public Comment convertDtoToEntity(RemoveCommentRequestDto commentDto) {
        return Comment.builder()
                .seq(commentDto.getCommentSeq())
                .user(User.builder().seq(commentDto.getUserSeq()).build()).build();
    }
}
