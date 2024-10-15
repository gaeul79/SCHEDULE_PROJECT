//package com.sparta.schedule_project.repository;
//
//import com.sparta.schedule_project.entity.User;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.lang.reflect.Field;
//import java.time.LocalDate;
//
///**
// * User 데이터를 관리하는 레포지토리 클래스입니다.
// * JdbcTemplate을 이용하여 데이터베이스와 연결됩니다.
// * @author 김현정
// * @since 2024-10-03
// */
//@Repository
//public class UserRepository {
//    private final String USER_TABLE_NAME = "User";
//    private final JdbcTemplate jdbcTemplate;
//
//    /**
//     * JdbcTemplate 객체를 의존성 주입 방식으로 받아옵니다.
//     *
//     * @param jdbcTemplate JdbcTemplate 객체
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public UserRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    /**
//     * 회원가입을 위한 유저 정보를 저장합니다.
//     *
//     * @param user 저장할 유저 정보
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public void createUser(User user) {
//        String query = makeInsertQuery(user);
//        jdbcTemplate.update(query);
//    }
//
//    /**
//     * 아이디로 유저 정보를 조회합니다.
//     *
//     * @param userDto 조회 조건 (아이디)
//     * @return 조회된 유저 정보 (UserDto)
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public User findById(User userDto) {
//        String query = makeFindByIdQuery(userDto);
//        return jdbcTemplate.query(query, resultSet -> {
//            if (resultSet.next()) {
//                User user = new User();
//                user.setUserId(resultSet.getString("user_id"));
//                user.setPassword(resultSet.getString("password"));
//                return user;
//            } else {
//                return null;
//            }
//        });
//    }
//
//    /**
//     * 조건에 맞는 유저 정보를 조회합니다.
//     *
//     * @param userDto 조회 조건
//     * @return 조회된 유저 정보 (UserDto)
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public User searchUser(User userDto) {
//        String query = makeSearchQuery(userDto);
//        return jdbcTemplate.query(query, resultSet -> {
//            if (resultSet.next()) {
//                User user = new User();
//                user.setUserId(resultSet.getString("user_id"));
//                user.setPassword(resultSet.getString("password"));
//                user.setName(resultSet.getString("name"));
//                user.setEmail(resultSet.getString("email"));
//                user.setCreateDate(resultSet.getObject("createDate", LocalDate.class));
//                user.setUpdateDate(resultSet.getObject("updateDate", LocalDate.class));
//                return user;
//            } else {
//                return null;
//            }
//        });
//    }
//
//    /**
//     * 유저 정보를 수정합니다.
//     *
//     * @param user 수정할 유저 정보
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public void updateUser(User user) {
//        String query = makeUpdateQuery(user);
//        jdbcTemplate.update(query);
//    }
//
//    /**
//     * 사용자를 데이터베이스에서 삭제합니다.
//     *
//     * @param user 삭제할 사용자 정보
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    public void deleteUser(User user) {
//        String query = makeDeleteQuery(user);
//        jdbcTemplate.update(query);
//    }
//
//    /**
//     * 사용자 등록을 위한 SQL 쿼리 생성
//     *
//     * @param user 사용자 정보
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeInsertQuery(User user) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("INSERT INTO ");
//        sql.append(USER_TABLE_NAME);
//        sql.append(" (");
//
//        StringBuilder sqlValues = new StringBuilder(" VALUES (");
//        if (user.getUserId() != null) {
//            sql.append("user_id, ");
//            sqlValues.append("'").append(user.getUserId()).append("', ");
//        }
//
//        if (user.getPassword() != null) {
//            sql.append("password, ");
//            sqlValues.append("'").append(user.getPassword()).append("', ");
//        }
//
//        if (user.getEmail() != null) {
//            sql.append("email, ");
//            sqlValues.append("'").append(user.getEmail()).append("', ");
//        }
//
//        if (user.getName() != null) {
//            sql.append("name");
//            sqlValues.append("'").append(user.getName()).append("'");
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
//     * id와 일치하는 사용자 조회를 위한 SQL 쿼리를 생성합니다.
//     *
//     * @param user 조회 조건
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeFindByIdQuery(User user) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT ");
//        sql.append("user_id");
//        sql.append(", password");
//        sql.append(" FROM ");
//        sql.append(USER_TABLE_NAME);
//        sql.append(makeWhere(user.getUserId()));
//        return sql.toString();
//    }
//
//    /**
//     * 사용자 조회를 위한 SQL 쿼리를 생성합니다.
//     *
//     * @param user 조회 조건
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeSearchQuery(User user) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("SELECT ");
//        sql.append("user_id");
//        sql.append(", password");
//        sql.append(", email");
//        sql.append(", name");
//        sql.append(", createDate");
//        sql.append(", updateDate");
//        sql.append(" FROM ");
//        sql.append(USER_TABLE_NAME);
//        sql.append(makeWhere(user));
//        return sql.toString();
//    }
//
//    /**
//     * 사용자 수정을 위한 SQL 쿼리 생성
//     *
//     * @param user 수정된 사용자 정보
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeUpdateQuery(User user) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("UPDATE ");
//        sql.append(USER_TABLE_NAME);
//
//        StringBuilder setValues = new StringBuilder();
//        Field[] fields = user.getClass().getDeclaredFields();
//
//        for (Field field : fields) {
//            String fieldName = field.getName();
//
//            if (fieldName.equals("password") && user.getPassword() != null) {
//                if (setValues.isEmpty())
//                    setValues.append(" SET ");
//                else
//                    setValues.append(", ");
//
//                setValues.append("password='").append(user.getPassword()).append("'");
//            }
//
//            if (fieldName.equals("email") && user.getEmail() != null) {
//                if (setValues.isEmpty())
//                    setValues.append(" SET ");
//                else
//                    setValues.append(", ");
//
//                setValues.append("email='").append(user.getEmail()).append("'");
//            }
//
//            if (fieldName.equals("name") && user.getName() != null) {
//                if (setValues.isEmpty())
//                    setValues.append(" SET ");
//                else
//                    setValues.append(", ");
//
//                setValues.append("name='").append(user.getName()).append("'");
//            }
//        }
//
//        User whereDto = new User();
//        whereDto.setUserId(user.getUserId());
//        String whereQuery = makeWhere(whereDto);
//
//        sql.append(setValues).append(whereQuery);
//        return sql.toString();
//    }
//
//    /**
//     * 사용자 삭제를 위한 SQL 쿼리 생성
//     *
//     * @param user 삭제할 사용자 정보
//     * @return 생성된 SQL 쿼리
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeDeleteQuery(User user) {
//        StringBuilder sql = new StringBuilder();
//        sql.append("DELETE FROM ");
//        sql.append(USER_TABLE_NAME);
//        sql.append(makeWhere(user));
//        return sql.toString();
//    }
//
//    /**
//     * WHERE 조건을 생성합니다.
//     *
//     * @param id 사용자 아이디
//     * @return 생성된 WHERE 조건 문자열
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeWhere(String id) {
//        StringBuilder sql = new StringBuilder();
//        sql.append(" WHERE ");
//        sql.append("user_id='").append(id).append("'");
//        return sql.toString();
//    }
//
//    /**
//     * WHERE 조건을 생성합니다.
//     *
//     * @param user 조회 조건
//     * @return 생성된 WHERE 조건 문자열
//     * @author 김현정
//     * @since 2024-10-03
//     */
//    private String makeWhere(User user) {
//        StringBuilder sql = new StringBuilder();
//
//        if (user.getUserId() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append(" and ");
//
//            sql.append("user_id='").append(user.getUserId()).append("'");
//        }
//
//        if (user.getPassword() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append(" and ");
//
//            sql.append("password='").append(user.getPassword()).append("'");
//        }
//
//        if (user.getEmail() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append(" and ");
//
//            sql.append("email='").append(user.getEmail()).append("'");
//        }
//
//        if (user.getName() != null) {
//            if (sql.isEmpty())
//                sql.append(" WHERE ");
//            else
//                sql.append(" and ");
//
//            sql.append("name='").append(user.getName()).append("'");
//        }
//
//        return sql.toString();
//    }
//}