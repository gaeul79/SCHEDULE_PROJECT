package com.sparta.schedule_project.dto.request.comment;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyCommentRequestDto {
    @NotBlank(message = "댓글번호는 공백일 수 없습니다.")
    private int commentSeq;

    @NotBlank(message = "유저번호는 공백일 수 없습니다.")
    private int userSeq;

    @NotBlank(message = "댓글내용을 입력해주세요.")
    @Max(value = 300, message = "댓글은 300자 이상 입력할 수 없습니다.")
    private String content;

    public static Comment to(ModifyCommentRequestDto commentDto) {
        return Comment.builder()
                .seq(commentDto.getCommentSeq())
                .user(User.builder().seq(commentDto.getUserSeq()).build())
                .content(commentDto.getContent()).build();
    }
}
