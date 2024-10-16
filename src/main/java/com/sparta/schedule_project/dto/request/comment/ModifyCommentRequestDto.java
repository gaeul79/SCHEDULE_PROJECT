package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyCommentRequestDto {
    @Positive(message = "댓글번호는 0이상 숫자입니다.")
    private int commentSeq;

    @Positive(message = "유저번호는 0이상 숫자입니다.")
    private int userSeq;

    @NotBlank(message = "댓글내용을 입력해주세요.")
    @Max(value = 300, message = "댓글은 300자 이상 입력할 수 없습니다.")
    private String content;

    public Comment convertDtoToEntity(ModifyCommentRequestDto commentDto) {
        return Comment.builder()
                .seq(commentDto.getCommentSeq())
                .user(User.builder().seq(commentDto.getUserSeq()).build())
                .content(commentDto.getContent()).build();
    }
}
