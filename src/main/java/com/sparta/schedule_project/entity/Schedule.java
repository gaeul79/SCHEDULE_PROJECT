package com.sparta.schedule_project.entity;

import com.sparta.schedule_project.dto.request.create.CreateScheduleRequestDto;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 일정 정보를 담는 Entity 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @Transient
    private LocalDate startUpdateDate;

    @Transient
    private LocalDate endUpdateDate;

    @Transient
    private Pageable page;

    public Schedule(CreateScheduleRequestDto requestDto) {
//        this.id = requestDto.getId();
//        this.userSeq = requestDto.getUserId();
//        this.title = requestDto.getTitle();
//        this.content = requestDto.getContent();
//        this.startUpdateDate = requestDto.getStartUpdateDate();
//        this.endUpdateDate = requestDto.getEndUpdateDate();
    }
}