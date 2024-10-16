package com.sparta.schedule_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @OneToMany(mappedBy = "schedule",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
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

    public void update(Schedule schedule) {
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.weather = schedule.getWeather();
    }
}
