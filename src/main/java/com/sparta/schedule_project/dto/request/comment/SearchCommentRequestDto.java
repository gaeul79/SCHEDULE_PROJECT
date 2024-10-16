package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
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

    /**
     * DTO 객체를 엔티티 객체로 변환
     *
     * @param commentDto 요청 DTO 객체
     * @return 생성된 댓글 엔티티 객체
     * @since 2024-10-18
     */
    public Comment convertDtoToEntity(SearchCommentRequestDto commentDto) {
        return Comment.builder()
                .schedule(Schedule.builder().seq(commentDto.getScheduleSeq()).build())
                .page(commentDto.convertDtoToPageable()).build();
    }
}
