package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCommentRequestDto extends PageDto {
    @Positive(message = "일정번호는 0이상 숫자입니다.")
    private int scheduleSeq;

    public Comment convertDtoToEntity(SearchCommentRequestDto commentDto) {
        return Comment.builder()
                .schedule(Schedule.builder().seq(commentDto.getScheduleSeq()).build())
                .page(commentDto.convertDtoToPageable()).build();
    }
}
