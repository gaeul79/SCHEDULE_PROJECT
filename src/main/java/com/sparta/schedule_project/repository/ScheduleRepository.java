package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.dto.entity.ScheduleDto;
import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ScheduleDto createSchedule(ScheduleDto scheduleDto) {
        return null;
    }

    public List<ScheduleViewDto> searchScheduleView(ScheduleDto scheduleDto) {
        return null;
    }

    public void updateSchedule(ScheduleDto scheduleDto) {

    }

    public void deleteSchedule(ScheduleDto scheduleDto) {

    }
}
