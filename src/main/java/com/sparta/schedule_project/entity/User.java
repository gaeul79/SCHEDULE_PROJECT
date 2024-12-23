package com.sparta.schedule_project.entity;

import com.sparta.schedule_project.dto.request.ModifyUserRequestDto;
import com.sparta.schedule_project.emums.AuthType;
import com.sparta.schedule_project.emums.SocialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 회원 정보를 담는 Entity 클래스
 *
 * @since 2024-10-03
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 30)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private AuthType auth = AuthType.USER;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void update(ModifyUserRequestDto userDto, String newPassword) {
        this.name = userDto.getName();
        this.password = newPassword;
    }

    public void updateSocialType(SocialType socialType) {
        this.socialType = socialType;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
