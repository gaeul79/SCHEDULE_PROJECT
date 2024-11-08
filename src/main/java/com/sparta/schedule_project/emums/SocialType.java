package com.sparta.schedule_project.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 소셜 로그인 종류를 나타내는 enum
 *
 * @since 2024-10-31
 */
@Getter
@AllArgsConstructor
public enum SocialType {
    DEFAULT("default"),
    KAKAO("kakao");

    private final String name;

    public static SocialType getSocialType(String name) {
        for (SocialType socialType : SocialType.values()) {
            if (socialType.getName().equals(name)) {
                return socialType;
            }
        }
        return DEFAULT;
    }
}
