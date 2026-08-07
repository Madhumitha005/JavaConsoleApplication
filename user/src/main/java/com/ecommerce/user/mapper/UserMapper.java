/*
 * UserMapper.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.mapper;

import org.springframework.stereotype.Component;

import com.ecommerce.user.dto.LoginRequestDto;
import com.ecommerce.user.dto.SignupRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.dto.UserUpdateDto;
import com.ecommerce.user.entity.User;

/**
 * Converts DTO objects into entity objects
 * and entity objects into DTO objects
 */
@Component
public class UserMapper {

    // Converts SignupRequestDto into User
    public User toEntity(final SignupRequestDto dto) {

        if (dto == null) {

            return null;
        }

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }

    // Converts UserUpdateDto into User
    public User toEntity(final UserUpdateDto dto) {

        if (dto == null) {

            return null;
        }

        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());

        return user;
    }

    // Converts LoginRequestDto into User
    public User toEntity(final LoginRequestDto dto) {

        if (dto == null) {

            return null;
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        return user;
    }

    // Converts User into UserResponseDto
    public UserResponseDto toResponseDto(final User user) {

        if (user == null) {

            return null;
        }

        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}