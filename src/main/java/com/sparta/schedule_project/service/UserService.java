package com.sparta.schedule_project.service;

import com.sparta.schedule_project.exception.ResponseCode;
import com.sparta.schedule_project.dto.ResponseStatusDto;
import com.sparta.schedule_project.dto.UserRequestDto;
import com.sparta.schedule_project.dto.UserResponseDto;
import com.sparta.schedule_project.dto.entity.UserDto;
import com.sparta.schedule_project.exception.ResponseException;
import com.sparta.schedule_project.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseStatusDto login(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            UserDto resultUser = userRepository.findById(user);
            checkUser(user, resultUser);
            return new ResponseStatusDto(ResponseCode.SUCCESS_LOGIN);
        } catch (ResponseException ex) {
            return new ResponseStatusDto(ex.getResponseCode());
        }
    }

    public void checkUser(UserDto inputUser, UserDto resultUser) throws ResponseException {
        if(resultUser == null)
            throw new ResponseException(ResponseCode.USER_NAME_NOT_FOUND);
        else if(!inputUser.getPassword().equals(resultUser.getPassword()))
            throw new ResponseException(ResponseCode.USER_NAME_NOT_FOUND);
    }

    public ResponseStatusDto logout() {
        try {
            return new ResponseStatusDto(ResponseCode.SUCCESS_LOGOUT);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public ResponseStatusDto createUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            userRepository.createUser(user);
            return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public UserResponseDto searchUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            UserDto resultUser = userRepository.searchUser(user);
            return UserResponseDto.from(resultUser, ResponseCode.SUCCESS_SEARCH_USER);
        } catch (Exception ex) {
            return UserResponseDto.from(null, ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public ResponseStatusDto updateUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            userRepository.updateUser(user);
            return new ResponseStatusDto(ResponseCode.SUCCESS_UPDATE_USER);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }

    public ResponseStatusDto deleteUser(UserRequestDto requestUserDto) {
        try {
            UserDto user = UserDto.from(requestUserDto);
            userRepository.deleteUser(user);
            return new ResponseStatusDto(ResponseCode.SUCCESS_CREATE_USER);
        } catch (Exception ex) {
            return new ResponseStatusDto(ResponseCode.UNKNOWN_ERROR, ex.getMessage());
        }
    }
}
