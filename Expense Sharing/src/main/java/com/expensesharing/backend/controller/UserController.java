package com.expensesharing.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensesharing.backend.dto.UserDto;
import com.expensesharing.backend.service.UserService;

@RestController
@RequestMapping("/api/users") // Base URL: http://localhost:8080/api/users
public class UserController {

    @Autowired
    private UserService userService;

    // POST http://localhost:8080/api/users
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }
}