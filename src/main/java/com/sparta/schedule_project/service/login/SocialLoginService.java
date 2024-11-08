package com.sparta.schedule_project.service.login;

import com.sparta.schedule_project.emums.SocialType;
import com.sparta.schedule_project.util.login.SocialLogin;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * 다양한 소셜 로그인 서비스를 관리하는 클래스
 * @since 2024-10-31
 */
@Component
public class SocialLoginService extends HashMap<SocialType, SocialLogin> {
    public SocialLoginService(KakaoLoginService kakaoLoginService) {
        put(SocialType.KAKAO, kakaoLoginService);
    }
}
