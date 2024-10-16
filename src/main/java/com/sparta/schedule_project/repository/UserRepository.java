package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * User 데이터를 관리하는 레포지토리 클래스입니다.
 *
 * @author 김현정
 * @since 2024-10-03
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findBySeq(int seq);

    User findByEmail(String email);
}