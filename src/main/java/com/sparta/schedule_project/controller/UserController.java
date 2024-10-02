package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.UserRequestDto;
import com.sparta.schedule_project.dto.UserResponseDto;
import com.sparta.schedule_project.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.sparta.com")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody UserRequestDto userRequestDto) {
        return userService.searchUser(userRequestDto);
    }

    @PostMapping("/logout")
    public UserResponseDto logout() {
        return userService.logout();
    }

    @PostMapping("/logout")
    public UserResponseDto getUserInfo(@RequestBody UserRequestDto userRequestDto) {
        return userService.searchUser(userRequestDto);
    }

    @PostMapping("/users")
    public UserResponseDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @PutMapping("/users/{userId}")
    public UserResponseDto updateUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(userRequestDto);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@RequestBody UserRequestDto userRequestDto) {
        userService.deleteUser(userRequestDto);
    }
}
