package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCommentRequestDto {
    @NotBlank(message = "일정번호는 공백일 수 없습니다.")
    private int scheduleSeq;

    @NotBlank(message = "댓글내용을 입력해주세요.")
    @Max(value = 300, message = "댓글은 300자 이상 입력할 수 없습니다.")
    private String content;

    public static Comment to(CreateCommentRequestDto commentDto) {
        return Comment.builder()
                .seq(commentDto.getScheduleSeq())
                .content(commentDto.getContent()).build();
    }
}
