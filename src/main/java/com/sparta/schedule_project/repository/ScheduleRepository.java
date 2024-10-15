//package com.sparta.schedule_project.repository;
//
//import com.sparta.schedule_project.entity.Schedule;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.lang.reflect.Field;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;
//
///**
// * 일정 데이터를 관리하는 레포지토리 클래스
// * JDBC를 사용하여 데이터베이스와 상호작용합니다.
// *
// * @author 김현정
// * @since 2024-10-03
// */
//@Repository
//public class ScheduleRepository {
//    private final String SCHEDULE_TABLE_NAME = "Schedule";
//    private final String SCHEDULE_VIEW_NAME = "ScheduleView";
//
//    private final JdbcTemplate jdbcTemplate;
//
//    /**
//     * JdbcTemplate 객체를 의존성 주입 방식으로 받아옵니다.
//     *
//     * @param jdbcTemplate JDBC 템플릿 객체
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public ScheduleRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    /**
//     * 일정 등록
//     *
//     * @param schedule 일정 정보
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public void createSchedule(Schedule schedule) {
//        String query = makeInsertQuery(schedule);
//        jdbcTemplate.update(query);
//    }
//
//    /**
//     * 일정 조회
//     *
//     * @param scheduleViewDto 조회 조건을 담은 객체
//     * @return 조회된 일정 목록
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public List<ScheduleView> searchSchedules(ScheduleView scheduleViewDto) {
//        String query = makeSearchQuery(scheduleViewDto);
//        return jdbcTemplate.query(query, new RowMapper<ScheduleView>() {
//            @Override
//            public ScheduleView mapRow(ResultSet rs, int rowNum) throws SQLException {
//                ScheduleView scheduleViewDto = ScheduleView.builder()
//                        .id(rs.getString("id"))
//                        .userId(rs.getString("user_id"))
//                        .name(rs.getString("name"))
//                        .title(rs.getString("title"))
//                        .content(rs.getString("content"))
//                        .createDate(rs.getDate("createDate").toLocalDate())
//                        .updateDate(rs.getDate("updateDate").toLocalDate())
//                        .build();
//                return scheduleViewDto;
//            }
//        });
//    }
//
//    /**
//     * 일정 조회 결과의 개수를 반환
//     *
//     * @param scheduleViewDto 조회 조건
//     * @return 조회된 일정 개수
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public Integer searchScheduleCount(ScheduleView scheduleViewDto) {
//        String query = makeCountQuery(scheduleViewDto);
//        return jdbcTemplate.queryForObject(query, Integer.class);
//    }
//
//    /**
//     * 일정 수정
//     *
//     * @param schedule 수정된 일정 정보
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public void updateSchedule(Schedule schedule) {
//        String query = makeUpdateQuery(schedule);
//        jdbcTemplate.update(query);
//    }
//
//    /**
//     * 일정을 삭제합니다.
//     *
//     * @param schedule 삭제할 일정 정보
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public void deleteSchedule(Schedule schedule) {
//        String query = makeDeleteQuery(schedule);
//        jdbcTemplate.update(query);
//    }
//
//    /**
//     * 일정 등록을 위한 SQL 쿼리를 생성합니다.
//     *
//     * @param schedule 일정 정보
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeInsertQuery(Schedule schedule) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("INSERT INTO ");
//        sql.append(SCHEDULE_TABLE_NAME);
//        sql.append(" (");
//
//        StringBuilder sqlValues = new StringBuilder(" VALUES (");
//        if (schedule.getUserSeq() != null) {
//            sql.append("user_id, ");
//            sqlValues.append("'").append(schedule.getUserSeq()).append("', ");
//        }
//
//        if (schedule.getTitle() != null) {
//            sql.append("title, ");
//            sqlValues.append("'").append(schedule.getTitle()).append("', ");
//        }
//
//        if (schedule.getContent() != null) {
//            sql.append("content");
//            sqlValues.append("'").append(schedule.getContent()).append("'");
//            ;
//        }
//
//        sqlValues.append(")");
//        sql.append(")");
//        sql.append(sqlValues);
//
//        return sql.toString();
//    }
//
//    /**
//     * 일정 수정을 위한 SQL 쿼리를 생성합니다.
//     *
//     * @param schedule 수정된 일정 정보
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeUpdateQuery(Schedule schedule) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("UPDATE ");
//        sql.append(SCHEDULE_TABLE_NAME);
//
//        StringBuilder setValues = new StringBuilder(" SET ");
//        Field[] fields = schedule.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            String fieldName = field.getName();
//
//            if (fieldName.equals("title") && schedule.getTitle() != null) {
//                if (setValues.length() > 5)
//                    setValues.append(", ");
//
//                setValues.append("title='").append(schedule.getTitle()).append("'");
//            }
//
//            if (fieldName.equals("content") && schedule.getContent() != null) {
//                if (setValues.length() > 5)
//                    setValues.append(", ");
//
//                setValues.append("content='").append(schedule.getContent()).append("'");
//            }
//        }
//
//        Schedule whereDto = new Schedule();
//        whereDto.setId(schedule.getId());
//        String whereQuery = makeWhere(whereDto);
//
//        sql.append(setValues).append(whereQuery);
//        return sql.toString();
//    }
//
//    /**
//     * 일정 삭제를 위한 SQL 쿼리를 생성합니다.
//     *
//     * @param schedule 삭제할 일정 정보
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeDeleteQuery(Schedule schedule) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("DELETE FROM ");
//        sql.append(SCHEDULE_TABLE_NAME);
//        sql.append(makeWhere(schedule));
//        return sql.toString();
//    }
//
//    /**
//     * 일정 조회를 위한 SQL 쿼리를 생성합니다.
//     *
//     * @param scheduleDto 조회 조건
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeSearchQuery(ScheduleView scheduleDto) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT ");
//        sql.append("id");
//        sql.append(", user_id");
//        sql.append(", name");
//        sql.append(", title");
//        sql.append(", content");
//        sql.append(", createDate");
//        sql.append(", updateDate");
//        sql.append(" FROM ");
//        sql.append(SCHEDULE_VIEW_NAME);
//        sql.append(makeWhere(scheduleDto));
//        sql.append(" ORDER BY updateDate DESC");
//        sql.append(makePaging(scheduleDto));
//        return sql.toString();
//    }
//
//    /**
//     * 페이징 처리를 위한 SQL 조건을 생성합니다.
//     *
//     * @param schedule 조회 조건
//     * @return 생성된 페이징 조건 문자열
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makePaging(Schedule schedule) {
//        StringBuilder sql = new StringBuilder();
//        if (schedule.getPage() != null && schedule.getSize() != null) {
//            sql.append(" LIMIT ");
//            sql.append(schedule.getSize());
//            sql.append(" OFFSET ");
//            sql.append((schedule.getPage() - 1) * schedule.getSize());
//        }
//        return sql.toString();
//    }
//
//    /**
//     * 일정 조회 결과의 개수를 계산하기 위한 SQL 쿼리를 생성합니다.
//     *
//     * @param scheduleDto 조회 조건
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeCountQuery(ScheduleView scheduleDto) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT ");
//        sql.append("COUNT(*)");
//        sql.append(" FROM ");
//        sql.append(SCHEDULE_VIEW_NAME);
//        sql.append(makeWhere(scheduleDto));
//        return sql.toString();
//    }
//
//    /**
//     * WHERE 조건을 생성합니다.
//     *
//     * @param schedule (ScheduleDto)조회 조건
//     * @return 생성된 WHERE 조건 문자열
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeWhere(Schedule schedule) {
//        StringBuilder sql = new StringBuilder();
//
//        if (schedule.getId() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append("  and  ");
//
//            sql.append("id='").append(schedule.getId()).append("'");
//        }
//
//        if (schedule.getUserSeq() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append("  and  ");
//
//            sql.append("user_id='").append(schedule.getUserSeq()).append("'");
//        }
//
//        if (schedule.getTitle() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append("  and  ");
//
//            sql.append("title like '%").append(schedule.getTitle()).append("%'");
//        }
//
//        if (schedule.getContent() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append("  and  ");
//
//            sql.append("content like '%").append(schedule.getContent()).append("%'");
//        }
//
//        if (schedule.getStartUpdateDate() != null && schedule.getEndUpdateDate() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append("  and  ");
//
//            sql.append("updateDate between '").append(schedule.getStartUpdateDate()).append("'");
//            sql.append(" and '").append(schedule.getEndUpdateDate()).append("'");
//        }
//
//        return sql.toString();
//    }
//
//    /**
//     * WHERE 조건을 생성합니다.
//     *
//     * @param scheduleDto (ScheduleViewDto)조회 조건
//     * @return 생성된 WHERE 조건 문자열
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeWhere(ScheduleView scheduleDto) {
//        StringBuilder sql = new StringBuilder();
//
//        if (scheduleDto.getName() != null) {
//            if (sql.length() > 7)
//                sql.append(" and ");
//            else
//                sql.append(" WHERE ");
//
//            sql.append("name like '%").append(scheduleDto.getName()).append("%'");
//        }
//
//        if (scheduleDto.getId() != null) {
//            if (sql.length() > 7)
//                sql.append(" and ");
//            else
//                sql.append(" WHERE ");
//
//            sql.append("id='").append(scheduleDto.getId()).append("'");
//        }
//
//        if (scheduleDto.getUserId() != null) {
//            if (sql.length() > 7)
//                sql.append(" and ");
//            else
//                sql.append(" WHERE ");
//
//            sql.append("user_id='").append(scheduleDto.getUserId()).append("'");
//        }
//
//        if (scheduleDto.getTitle() != null) {
//            if (sql.length() > 7)
//                sql.append(" and ");
//            else
//                sql.append(" WHERE ");
//
//            sql.append("title like '%").append(scheduleDto.getTitle()).append("%'");
//        }
//
//        if (scheduleDto.getContent() != null) {
//            if (sql.length() > 7)
//                sql.append(" and ");
//            else
//                sql.append(" WHERE ");
//
//            sql.append("content like '%").append(scheduleDto.getContent()).append("%'");
//        }
//
//        if (scheduleDto.getStartUpdateDate() != null && scheduleDto.getEndUpdateDate() != null) {
//            if (sql.length() > 7)
//                sql.append(" and ");
//            else
//                sql.append(" WHERE ");
//
//            sql.append("updateDate between '").append(scheduleDto.getStartUpdateDate()).append("'");
//            sql.append(" and '").append(scheduleDto.getEndUpdateDate()).append("'");
//        }
//
//        return sql.toString();
//    }
//}
