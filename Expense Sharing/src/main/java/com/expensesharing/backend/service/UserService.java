package com.expensesharing.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expensesharing.backend.dto.UserDto;
import com.expensesharing.backend.model.User;
import com.expensesharing.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists!");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        User savedUser = userRepository.save(user);
        userDto.setId(savedUser.getId());
        return userDto;
    }
}