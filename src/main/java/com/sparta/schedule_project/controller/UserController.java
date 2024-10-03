package com.sparta.schedule_project.controller;

import com.sparta.schedule_project.dto.ResponseStatusDto;
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
    public ResponseStatusDto login(@RequestBody UserRequestDto userRequestDto) {
        return userService.login(userRequestDto);
    }

    @PostMapping("/logout")
    public ResponseStatusDto logout() {
        return userService.logout();
    }

    @PostMapping("/users")
    public ResponseStatusDto createUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.createUser(userRequestDto);
    }

    @GetMapping("/users")
    public UserResponseDto getUserInfo(@RequestBody UserRequestDto userRequestDto) {
        return userService.searchUser(userRequestDto);
    }

    @PutMapping("/users/{userId}")
    public ResponseStatusDto updateUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.updateUser(userRequestDto);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseStatusDto deleteUser(@RequestBody UserRequestDto userRequestDto) {
        return userService.deleteUser(userRequestDto);
    }
}
