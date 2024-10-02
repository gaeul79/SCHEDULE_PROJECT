package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.dto.entity.ScheduleDto;
import com.sparta.schedule_project.dto.entity.ScheduleViewDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ScheduleRepository {
    private final String SCHEDULE_TABLE_NAME = "Schedule";
    private final String SCHEDULE_VIEW_NAME = "ScheduleView";

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createSchedule(ScheduleDto scheduleDto) {
        String query = makeInsertQuery(scheduleDto);
        jdbcTemplate.update(query);
    }

    public List<ScheduleViewDto> searchSchedules(ScheduleViewDto userDto) {
        String query = makeSearchQuery(userDto);
        return jdbcTemplate.query(query, new RowMapper<ScheduleViewDto>() {
            @Override
            public ScheduleViewDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                ScheduleViewDto scheduleViewDto = new ScheduleViewDto();
                scheduleViewDto.setId(rs.getString("id"));
                scheduleViewDto.setUserId(rs.getString("user_id"));
                scheduleViewDto.setName(rs.getString("name"));
                scheduleViewDto.setTitle(rs.getString("title"));
                scheduleViewDto.setContent(rs.getString("content"));
                scheduleViewDto.setCreateDate(rs.getDate("createDate").toLocalDate());
                scheduleViewDto.setUpdateDate(rs.getDate("updateDate").toLocalDate());
                return scheduleViewDto;
            }
        });
    }

    public void updateSchedule(ScheduleDto scheduleDto) {
        String query = makeUpdateQuery(scheduleDto);
        jdbcTemplate.update(query);
    }

    public void deleteSchedule(ScheduleDto scheduleDto) {
        String query = makeDeleteQuery(scheduleDto);
        jdbcTemplate.update(query);
    }

    private String makeInsertQuery(ScheduleDto scheduleDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(SCHEDULE_TABLE_NAME);
        sql.append(" (");

        StringBuilder sqlValues = new StringBuilder(" VALUES (");
        if (scheduleDto.getUserId() != null) {
            sql.append("user_id, ");
            sqlValues.append("'").append(scheduleDto.getUserId()).append("', ");
        }

        if (scheduleDto.getTitle() != null) {
            sql.append("title, ");
            sqlValues.append("'").append(scheduleDto.getTitle()).append("', ");
        }

        if (scheduleDto.getContent() != null) {
            sql.append("content");
            sqlValues.append("'").append(scheduleDto.getContent()).append("'");;
        }

        sqlValues.append(")");
        sql.append(")");
        sql.append(sqlValues);

        return sql.toString();
    }

    private String makeUpdateQuery(ScheduleDto scheduleDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(SCHEDULE_TABLE_NAME);

        StringBuilder setValues = new StringBuilder(" SET ");
        Field[] fields = scheduleDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();

            if (fieldName.equals("title") && scheduleDto.getTitle() != null) {
                if (setValues.length() > 5)
                    setValues.append(", ");

                setValues.append("title='").append(scheduleDto.getTitle()).append("'");
            }

            if (fieldName.equals("content") && scheduleDto.getContent() != null) {
                if (setValues.length() > 5)
                    setValues.append(", ");

                setValues.append("content='").append(scheduleDto.getContent()).append("'");
            }
        }

        ScheduleDto whereDto = new ScheduleDto();
        whereDto.setId(scheduleDto.getId());
        String whereQuery = makeWhere(whereDto);

        sql.append(setValues).append(whereQuery);
        return sql.toString();
    }

    private String makeDeleteQuery(ScheduleDto scheduleDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(SCHEDULE_TABLE_NAME);
        sql.append(makeWhere(scheduleDto));
        return sql.toString();
    }

    private String makeSearchQuery(ScheduleViewDto scheduleDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("id");
        sql.append(", user_id");
        sql.append(", name");
        sql.append(", title");
        sql.append(", content");
        sql.append(", createDate");
        sql.append(", updateDate");
        sql.append(" FROM ");
        sql.append(SCHEDULE_VIEW_NAME);
        sql.append(makeWhere(scheduleDto));
        return sql.toString();
    }

    private String makeWhere(ScheduleDto scheduleDto) {
        StringBuilder sql = new StringBuilder();

        Field[] fields = scheduleDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();

            if (fieldName.equals("id") && scheduleDto.getId() != null) {
                if (sql.isEmpty())
                    sql.append(" WHERE ");
                else
                    sql.append("  and  ");

                sql.append("id='").append(scheduleDto.getId()).append("'");
            }

            if (fieldName.equals("userId") && scheduleDto.getUserId() != null) {
                if (sql.isEmpty())
                    sql.append(" WHERE ");
                else
                    sql.append("  and  ");

                sql.append("user_id='").append(scheduleDto.getUserId()).append("'");
            }

            if (fieldName.equals("title") && scheduleDto.getTitle() != null) {
                if (sql.isEmpty())
                    sql.append(" WHERE ");
                else
                    sql.append("  and  ");

                sql.append("title like '%").append(scheduleDto.getTitle()).append("%'");
            }

            if (fieldName.equals("content") && scheduleDto.getContent() != null) {
                if (sql.isEmpty())
                    sql.append(" WHERE ");
                else
                    sql.append("  and  ");

                sql.append("content like '%").append(scheduleDto.getContent()).append("%'");
            }

            if(fieldName.equals("startDate")) {
                if(scheduleDto.getStartUpdateDate() != null && scheduleDto.getEndUpdateDate() != null) {
                    if (sql.isEmpty())
                        sql.append(" WHERE ");
                    else
                        sql.append("  and  ");

                    sql.append("updateDate between '").append(scheduleDto.getStartUpdateDate()).append("'");
                    sql.append(" and '").append(scheduleDto.getEndUpdateDate()).append("'");
                }
            }
        }

        return sql.toString();
    }

    private String makeWhere(ScheduleViewDto scheduleDto) {
        StringBuilder sql = new StringBuilder();
        Field[] fields = scheduleDto.getClass().getDeclaredFields();
        Field[] superFields = scheduleDto.getClass().getSuperclass().getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.equals("name") && scheduleDto.getName() != null) {
                if (sql.length() > 7)
                    sql.append(" and ");
                else
                    sql.append(" WHERE ");

                sql.append("name like '%").append(scheduleDto.getName()).append("%'");
            }
        }

        for (Field superField : superFields) {
            String fieldName = superField.getName();

            if (fieldName.equals("id") && scheduleDto.getId() != null) {
                if (sql.length() > 7)
                    sql.append(" and ");
                else
                    sql.append(" WHERE ");

                sql.append("id='").append(scheduleDto.getId()).append("'");
            }

            if (fieldName.equals("userId") && scheduleDto.getUserId() != null) {
                if (sql.length() > 7)
                    sql.append(" and ");
                else
                    sql.append(" WHERE ");

                sql.append("user_id='").append(scheduleDto.getUserId()).append("'");
            }

            if (fieldName.equals("title") && scheduleDto.getTitle() != null) {
                if (sql.length() > 7)
                    sql.append(" and ");
                else
                    sql.append(" WHERE ");

                sql.append("title like '%").append(scheduleDto.getTitle()).append("%'");
            }

            if (fieldName.equals("content") && scheduleDto.getContent() != null) {
                if (sql.length() > 7)
                    sql.append(" and ");
                else
                    sql.append(" WHERE ");

                sql.append("content like '%").append(scheduleDto.getContent()).append("%'");
            }

            if(fieldName.equals("startUpdateDate")) {
                if(scheduleDto.getStartUpdateDate() != null && scheduleDto.getEndUpdateDate() != null) {
                    if (sql.length() > 7)
                        sql.append(" and ");
                    else
                        sql.append(" WHERE ");

                    sql.append("updateDate between '").append(scheduleDto.getStartUpdateDate()).append("'");
                    sql.append(" and '").append(scheduleDto.getEndUpdateDate()).append("'");
                }
            }
        }

        return sql.toString();
    }
}
