package com.sparta.schedule_project.dto.request;

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
}
