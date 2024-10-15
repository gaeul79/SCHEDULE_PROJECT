package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor
public class SearchCommentRequestDto {
    @NotBlank(message = "일정번호는 공백일 수 없습니다.")
    private int scheduleSeq;

    @NotBlank(message = "페이지는 공백일 수 없습니다.")
    private Pageable page;

    public static Comment to(SearchCommentRequestDto commentDto) {
        return Comment.builder()
                .schedule(Schedule.builder().seq(commentDto.scheduleSeq).build())
                .page(commentDto.getPage()).build();
    }
}
