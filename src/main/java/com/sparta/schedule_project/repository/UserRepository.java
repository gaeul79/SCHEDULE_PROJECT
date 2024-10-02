package com.sparta.schedule_project.repository;

import com.sparta.schedule_project.dto.entity.UserDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.time.LocalDate;

@Repository
public class UserRepository {
    private final String TablelName = "User";
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUser(UserDto userDto) {
        String query = makeInsertQuery(userDto);
        jdbcTemplate.update(query);
    }

    public UserDto findById(UserDto userDto) {
        String query = makeFindByIdQuery(userDto);
        return jdbcTemplate.query(query, resultSet -> {
            if(resultSet.next()) {
                UserDto user = new UserDto();
                user.setUserId(resultSet.getString("user_id"));
                user.setPassword(resultSet.getString("password"));
                return user;
            } else {
                return null;
            }
        });
    }

    public UserDto searchUser(UserDto userDto) {
        String query = makeSearchQuery(userDto);
        return jdbcTemplate.query(query, resultSet -> {
            if(resultSet.next()) {
                UserDto user = new UserDto();
                user.setUserId(resultSet.getString("user_id"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setCreateDate(resultSet.getObject("createDate", LocalDate.class));
                user.setUpdateDate(resultSet.getObject("updateDate", LocalDate.class));
                return user;
            } else {
                return null;
            }
        });
    }

    public void updateUser(UserDto requestUserDto) {
        String query = makeUpdateQuery(requestUserDto);
        jdbcTemplate.update(query);
    }

    public void deleteUser(UserDto userDto) {
        String query = makeDeleteQuery(userDto);
        jdbcTemplate.update(query);
    }

    private String makeInsertQuery(UserDto userDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(TablelName);
        sql.append(" (");

        StringBuilder sqlValues = new StringBuilder(" VALUES (");
        if (userDto.getUserId() != null) {
            sql.append("user_id, ");
            sqlValues.append("'").append(userDto.getUserId()).append("', ");
        }

        if (userDto.getPassword() != null) {
            sql.append("password, ");
            sqlValues.append("'").append(userDto.getPassword()).append("', ");
        }

        if (userDto.getEmail() != null) {
            sql.append("email, ");
            sqlValues.append("'").append(userDto.getEmail()).append("', ");
        }

        if (userDto.getName() != null) {
            sql.append("name");
            sqlValues.append("'").append(userDto.getName()).append("'");
        }

        sqlValues.append(")");
        sql.append(")");
        sql.append(sqlValues);

        return sql.toString();
    }

    private String makeFindByIdQuery(UserDto userDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("user_id");
        sql.append(", password");
        sql.append(" FROM ");
        sql.append(TablelName);
        sql.append(makeWhere(userDto));
        return sql.toString();
    }

    private String makeSearchQuery(UserDto userDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("user_id");
        sql.append(", password");
        sql.append(", email");
        sql.append(", name");
        sql.append(", createDate");
        sql.append(", updateDate");
        sql.append(" FROM ");
        sql.append(TablelName);
        sql.append(makeWhere(userDto));
        return sql.toString();
    }

    private String makeUpdateQuery(UserDto userDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(TablelName);

        StringBuilder setValues = new StringBuilder(" SET ");
        Field[] fields = userDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();

            if (fieldName.equals("password") && userDto.getPassword() != null) {
                if (setValues.length() > 5)
                    setValues.append(", ");

                setValues.append("password='").append(userDto.getPassword()).append("'");
            }

            if (fieldName.equals("email") && userDto.getEmail() != null) {
                if (setValues.length() > 5)
                    setValues.append(", ");

                setValues.append("email='").append(userDto.getEmail()).append("'");
            }

            if (fieldName.equals("name") && userDto.getName() != null) {
                if (setValues.length() > 5)
                    setValues.append(", ");

                setValues.append("name='").append(userDto.getName()).append("'");
            }
        }

        UserDto whereDto = new UserDto();
        whereDto.setUserId(userDto.getUserId());
        String whereQuery = makeWhere(whereDto);

        sql.append(setValues).append(whereQuery);
        return sql.toString();
    }

    private String makeDeleteQuery(UserDto userDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("DELETE FROM ");
        sql.append(TablelName);
        sql.append(makeWhere(userDto));
        return sql.toString();
    }

    private String makeWhere(UserDto userDto) {
        StringBuilder sql = new StringBuilder();
        sql.append(" WHERE ");

        Field[] fields = userDto.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.equals("userId") && userDto.getUserId() != null) {
                if(sql.length() > 7)
                    sql.append(" and ");

                sql.append("user_id='").append(userDto.getUserId()).append("'");
            }

            if (fieldName.equals("password") && userDto.getPassword() != null) {
                if(sql.length() > 7)
                    sql.append(" and ");

                sql.append("password='").append(userDto.getPassword()).append("'");
            }

            if (fieldName.equals("email") && userDto.getEmail() != null) {
                if(sql.length() > 7)
                    sql.append(" and ");

                sql.append("email='").append(userDto.getEmail()).append("'");
            }

            if (fieldName.equals("name") && userDto.getName() != null) {
                if(sql.length() > 7)
                    sql.append(" and ");

                sql.append("name='").append(userDto.getName()).append("'");
            }
        }

        return sql.toString();
    }
}