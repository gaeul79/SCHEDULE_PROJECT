package com.sparta.schedule_project.common.repository;

import com.sparta.schedule_project.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 유저 엔티티를 위한 JPA 레포지토리입니다.
 *
 * @since 2024-10-17
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findBySeq(int seq);

    User findByEmail(String email);

//    default User findByUsername(String username) throws ResponseException {
//        return findByName(username).orElseThrow(()-> new ResponseException(ResponseCode.USER_NOT_FOUND));
//    }
}