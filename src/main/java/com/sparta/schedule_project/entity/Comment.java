package com.sparta.schedule_project.entity;


import jakarta.persistence.*;
import lombok.*;

import java.awt.print.Pageable;


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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "schedule_seq")
    private Schedule schedule;

    @Column(name = "content", nullable = false, length = 300)
    private String content;

    @Transient
    private Pageable page;
}
