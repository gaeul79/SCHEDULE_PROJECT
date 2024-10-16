package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RemoveCommentRequestDto {
    @Positive(message = "댓글번호는 0이상 숫자입니다.")
    private int commentSeq;

    @Positive(message = "유저번호는 0이상 숫자입니다.")
    private int userSeq;

    public Comment convertDtoToEntity(RemoveCommentRequestDto commentDto) {
        return Comment.builder()
                .seq(commentDto.getCommentSeq())
                .user(User.builder().seq(commentDto.getUserSeq()).build()).build();
    }
}
