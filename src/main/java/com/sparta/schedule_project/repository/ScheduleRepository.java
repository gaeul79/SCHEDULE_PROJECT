package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 일정 엔티티를 위한 JPA 레포지토리입니다.
 *
 * @since 2024-10-17
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    Schedule findById(int id);

    Page<Schedule> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
