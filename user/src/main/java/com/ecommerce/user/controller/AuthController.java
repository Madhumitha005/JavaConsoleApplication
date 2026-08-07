/*
 * AuthController.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.controller;

import java.util.Collection;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.common.response.ApiResponse;
import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.SignupRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.dto.UserUpdateDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.mapper.UserMapper;
import com.ecommerce.user.service.AuthService;

// Handles authentication operations
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;

    // Creates an AuthController object
    public AuthController(
            final AuthService authService,
            final UserMapper userMapper) {

        this.authService = authService;
        this.userMapper = userMapper;
    }

    // Registers a new user
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(
            @Valid
            @RequestBody
            final SignupRequestDto requestDto) {

        User user = userMapper.toEntity(requestDto);
        authService.signup(user);

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully", null));
    }

    // Authenticates a user
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(
            @Valid
            @RequestBody
            final LoginRequestDto requestDto) {

        User user = userMapper.toEntity(requestDto);
        User authenticatedUser = authService.login(user);

        return ResponseEntity.ok(userMapper.toResponseDto(authenticatedUser));
    }

    // Retrieves a user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDto> getUserByEmail(
            @PathVariable("email")
            final String email) {

        User user = authService.getUserByEmail(email);

        return ResponseEntity.ok(userMapper.toResponseDto(user));
    }

    // Retrieves all users
    @GetMapping
    public ResponseEntity<Collection<UserResponseDto>> findAll() {

        Collection<UserResponseDto> users = authService.findAll()
                        .stream()
                        .map(userMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(users);
    }

    // Updates user information
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable("userId")
            final Integer userId,
            @Valid
            @RequestBody
            final UserUpdateDto requestDto) {

        User user = userMapper.toEntity(requestDto);
        user.setId(userId);
        authService.update(user);

        return ResponseEntity.ok(new ApiResponse(true, "User updated successfully.", null));
    }

    // Deletes a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable("userId")
            final Integer userId) {

        authService.delete(userId);

        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully.",null));
    }
}