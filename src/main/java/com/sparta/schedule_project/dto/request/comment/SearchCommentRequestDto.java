package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.dto.PageDto;
import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCommentRequestDto extends PageDto {
    @NotBlank(message = "일정번호는 공백일 수 없습니다.")
    private int scheduleSeq;

    public static Comment convertDtoToEntity(SearchCommentRequestDto commentDto) {
        return Comment.builder()
                .schedule(Schedule.builder().seq(commentDto.getScheduleSeq()).build())
                .page(commentDto.convertDtoToPageable()).build();
    }
}
