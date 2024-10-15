package com.sparta.schedule_project.dto.response.comment;

import com.sparta.schedule_project.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int seq;
    private int userSeq;
    private String userName;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static CommentDto from(Comment comment) {
        return CommentDto.builder().seq(comment.getSeq())
                .userSeq(comment.getUser().getSeq())
                .userName(comment.getUser().getName())
                .content(comment.getContent())
                .createDate(comment.getCreateDate())
                .updateDate(comment.getUpdateDate()).build();
    }
}
