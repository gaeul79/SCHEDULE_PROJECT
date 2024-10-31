package com.sparta.schedule_project.service.login;

import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import jakarta.servlet.http.HttpServletResponse;

public interface SocialLogin {
    ResponseStatusDto login(HttpServletResponse res, String accessCode);
}
