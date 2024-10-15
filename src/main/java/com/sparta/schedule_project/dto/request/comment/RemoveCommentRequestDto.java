package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveCommentRequestDto {
    @NotBlank(message = "댓글번호는 공백일 수 없습니다.")
    private int commentSeq;

    @NotBlank(message = "유저번호는 공백일 수 없습니다.")
    private int userSeq;

    public static Comment to(RemoveCommentRequestDto commentDto) {
        return Comment.builder()
                .seq(commentDto.getCommentSeq())
                .user(User.builder().seq(commentDto.getUserSeq()).build()).build();
    }
}
