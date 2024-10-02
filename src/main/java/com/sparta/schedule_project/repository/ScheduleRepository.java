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

    //    public List<UserDto> searchUsers(UserDto userDto) {
//        String query = makeSearchQuery(userDto);
//        return jdbcTemplate.query(query, new RowMapper<UserDto>() {
//            @Override
//            public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
//                String userId = rs.getString("user_id");
//                String password = rs.getString("password");
//                String email = rs.getString("email");
//                String name = rs.getString("name");
//                LocalDate createDate = rs.getDate("createDate").toLocalDate();
//                LocalDate updateDate = rs.getDate("updateDate").toLocalDate();
//                return new UserDto(userId, password, email, name, createDate, updateDate);
//            }
//        });
//    }
}
