package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.entity.Comment;
import com.sparta.schedule_project.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Comment findBySeq(int seq);

    Page<Comment> findAllByScheduleOrderByUpdateDateDesc(Schedule schedule, Pageable pageable);
}
