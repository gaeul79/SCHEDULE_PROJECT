package com.sparta.schedule_project.entity;

import com.sparta.schedule_project.common.entity.User;
import com.sparta.schedule_project.dto.request.ModifyCommentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

/**
 * 댓글에 대한 댓글 엔티티 클래스
 *
 * @since 2024-10-18
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_seq")
    private Schedule schedule;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Transient
    private Pageable page;

    public void update(ModifyCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
