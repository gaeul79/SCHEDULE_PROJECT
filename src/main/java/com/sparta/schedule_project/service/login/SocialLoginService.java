package com.sparta.schedule_project.service.login;

import com.sparta.schedule_project.emums.SocialType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SocialLoginService extends HashMap<SocialType, SocialLogin> {
    public SocialLoginService(KakaoLoginService kakaoLoginService) {
        put(SocialType.KAKAO, kakaoLoginService);
    }
}
