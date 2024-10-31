package com.sparta.schedule_project.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.schedule_project.dto.response.ResponseStatusDto;
import com.sparta.schedule_project.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SocialLogin {
    ResponseStatusDto login(HttpServletRequest req, HttpServletResponse res, String accessCode) throws JsonProcessingException;
    String getToken(String accessCode) throws JsonProcessingException;
    User getUser(String accessToken) throws JsonProcessingException;
    void signUp(User user);
}
