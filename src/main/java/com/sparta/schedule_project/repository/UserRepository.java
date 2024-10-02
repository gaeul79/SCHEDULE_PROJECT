package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.dto.entity.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserDto createUser(UserDto requestUserDto) {
        return null;
    }

    public UserDto searchUser(UserDto requestUserDto) {
        return null;
    }

    public void updateUser(UserDto requestUserDto) {

    }

    public void deleteUser(UserDto userDto) {

    }
}
