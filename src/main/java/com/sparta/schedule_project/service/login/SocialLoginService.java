package com.sparta.schedule_project.service.login;

import com.sparta.schedule_project.emums.LoginType;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SocialLoginService extends HashMap<LoginType, SocialLogin> {
    public SocialLoginService() {
        put(LoginType.KAKAO, new KakaoLoginService());
    }
}
