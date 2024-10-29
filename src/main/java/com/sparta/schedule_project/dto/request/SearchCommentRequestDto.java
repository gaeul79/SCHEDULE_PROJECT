package com.sparta.schedule_project.dto.request;

import com.sparta.schedule_project.dto.PageDto;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 댓글 조회 요청 DTO 클래스
 *
 * @since 2024-10-18
 */
@Getter
@NoArgsConstructor
public class SearchCommentRequestDto extends PageDto {
    @Positive(message = "일정번호는 0이상 숫자입니다.")
    private int scheduleSeq;
}
