package com.sparta.schedule_project.entity;

import com.sparta.schedule_project.dto.request.schedule.CreateScheduleRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule")
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

    @Column(nullable = false, length = 30)
    private String weather;

    @Transient
    private LocalDate startUpdateDate;

    @Transient
    private LocalDate endUpdateDate;

    @Transient
    private Pageable page;
}
