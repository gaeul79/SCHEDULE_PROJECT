package com.sparta.schedule_project.dto.response;

import com.sparta.schedule_project.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 댓글 정보를 담는 응답 DTO 클래스입니다.
 * 서버에서 클라이언트에게 댓글 정보를 전달할 때 사용됩니다.
 *
 * @since 2024-10-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private int id;
    private int userId;
    private String userName;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Comment 엔티티 객체를 CommentDto 객체로 변환합니다.
     *
     * @param comment 변환할 Comment 엔티티 객체
     * @return 변환된 CommentDto 객체
     * @since 2024-10-18
     */
    public static CommentDto from(Comment comment) {
        return CommentDto.builder().id(comment.getId())
                .userId(comment.getUser().getId())
                .userName(comment.getUser().getName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt()).build();
    }
}
