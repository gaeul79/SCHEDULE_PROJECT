package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.UserRequestDto;
import com.sparta.schedule_project.dto.UserResponseDto;
import com.sparta.schedule_project.dto.entity.UserDto;
import com.sparta.schedule_project.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto logout() {
        UserResponseDto response = new UserResponseDto();
        return response;
    }

    public UserResponseDto createUser(UserRequestDto requestUserDto) {
        UserDto user = new UserDto();
        UserDto resultUser = userRepository.createUser(user);
        UserResponseDto response = new UserResponseDto();
        return response;
    }

    public UserResponseDto searchUser(UserRequestDto requestUserDto) {
        UserDto user = new UserDto();
        UserDto resultUser = userRepository.searchUser(user);
        UserResponseDto response = new UserResponseDto();
        return response;
    }

    public UserResponseDto updateUser(UserRequestDto requestUserDto) {
        UserDto user = new UserDto();
        userRepository.updateUser(user);
        UserResponseDto response = new UserResponseDto();
        return response;
    }

    public UserResponseDto deleteUser(UserRequestDto requestUserDto) {
        UserDto user = new UserDto();
        userRepository.deleteUser(user);
        UserResponseDto response = new UserResponseDto();
        return response;
    }
}
