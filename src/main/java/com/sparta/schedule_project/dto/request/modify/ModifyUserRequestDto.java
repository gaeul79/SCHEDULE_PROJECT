package com.sparta.schedule_project.dto.request.modify;

import lombok.Data;

/**
 * 사용자 정보를 요청할 때 사용하는 DTO 클래스
 *
 * @author 김현정
 * @since 2024-10-03
 */
@Data
public class ModifyUserRequestDto {
    private String userId;
    private String password;
    private String name;
    private String email;
}
