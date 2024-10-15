package com.sparta.schedule_project.entity;

import com.sparta.schedule_project.common.AuthType;
import com.sparta.schedule_project.dto.request.create.CreateUserRequestDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 회원 정보를 담는 Entity 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
@Entity
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seq;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 30)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private AuthType auth;

    @OneToMany(mappedBy = "user")
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    /**
     * UserRequestDto 객체로부터 회원 정보를 복사하여 UserDto 객체를 생성합니다.
     *
     * @param user 회원 정보가 담긴 UserRequestDto 객체
     * @return 생성된 UserDto 객체
     * @author 김현정
     * @since 2024-10-03
     */
    public static User from(CreateUserRequestDto user) {
        User userDto = new User();
//        userDto.setSeq(user.getUserId());
//        userDto.setPassword(user.getPassword());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
