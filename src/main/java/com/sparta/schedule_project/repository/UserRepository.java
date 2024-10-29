package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 유저 엔티티를 위한 JPA 레포지토리입니다.
 *
 * @since 2024-10-17
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findById(int id);

    User findByEmail(String email);
}