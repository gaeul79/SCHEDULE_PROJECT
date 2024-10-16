package com.sparta.schedule_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security   에서 사용되는 비밀번호 암호화 설정을 담당하는 클래스입니다.
 *
 * @since 2024-10-17
 */
@Configuration
public class PasswordConfig {

    /**
     * Spring Security에서 사용되는 비밀번호 암호화 객체를 생성하여 Bean으로 등록합니다.
     * BCryptPasswordEncoder 클래스를 사용하여 비밀번호를 암호화합니다.
     *
     * @return Bean으로 등록된 PasswordEncoder 객체
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호를 암호화해주는 hash 함수
    }
}
