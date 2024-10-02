package com.sparta.schedule_project.service;

import com.sparta.schedule_project.dto.StatusDto;
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

    public StatusDto login(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            UserDto resultUser = userRepository.findById(user);
            // 비밀번호나 그런거 검사..
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }

    public StatusDto logout() {
        try {
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }

    public StatusDto createUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            userRepository.createUser(user);
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }

    public UserResponseDto searchUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            UserDto resultUser = userRepository.searchUser(user);
            StatusDto statusDto = new StatusDto(200, "Success");
            return UserResponseDto.from(resultUser, statusDto);
        } catch (Exception ex) {
            StatusDto statusDto = new StatusDto(999, "Failed");
            return UserResponseDto.from(null, statusDto);
        }
    }

    public StatusDto updateUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            userRepository.updateUser(user);
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }

    public StatusDto deleteUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            userRepository.deleteUser(user);
            return new StatusDto(200, "Success");
        } catch (Exception ex) {
            return new StatusDto(999, "Failed");
        }
    }
}
