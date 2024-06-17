package com.example.msemilbank.controller;

import com.example.msemilbank.model.get.UserGetDto;
import com.example.msemilbank.model.set.UserSetDto;
import com.example.msemilbank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public void saveUser(@RequestBody UserSetDto userSetDto) {
        userService.saveUser(userSetDto);
    }

    @GetMapping("/{userId}")
    public UserGetDto getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public List<UserGetDto> getAllUsers() {
        return userService.getAllUsers();
    }

}
