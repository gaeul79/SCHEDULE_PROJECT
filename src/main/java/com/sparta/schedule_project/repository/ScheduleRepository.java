package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.entity.Schedule;
import com.sparta.schedule_project.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 일정 데이터를 관리하는 레포지토리 클래스
 * JDBC를 사용하여 데이터베이스와 상호작용합니다.
 *
 * @author 김현정
 * @since 2024-10-03
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    Schedule findBySeq(int seq);

    Page<Schedule> findAllByOrderByUpdateDateDesc(Pageable pageable);
}
