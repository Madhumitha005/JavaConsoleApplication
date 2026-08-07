/*
 * AuthService.java
 *
 * Version 1.1
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.service;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ecommerce.common.exception.AuthenticationException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.util.PasswordUtil;
import com.ecommerce.common.util.StringUtil;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

/**
 * Provides authentication services for
 * signup and login operations.
 */
@Service
public class AuthService {

    private final UserRepository memoryRepository;
    private final UserRepository jdbcRepository;
    private final PasswordUtil passwordUtil;
    private final StringUtil stringUtil;

    // Creates an AuthService object
    public AuthService(
            @Qualifier("inMemoryUserRepository")
            final UserRepository memoryRepository,
            @Qualifier("jdbcUserRepository")
            final UserRepository jdbcRepository,
            final PasswordUtil passwordUtil,
            final StringUtil stringUtil) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
        this.passwordUtil = passwordUtil;
        this.stringUtil = stringUtil;
    }

    // Registers a new user
    public boolean signup(final User user) {

        if (user == null) {

            throw new NullPointerException("User cannot be null.");
        }

        user.setName(stringUtil.clean(user.getName()));
        user.setEmail(stringUtil.cleanEmail(user.getEmail()));
        user.setPassword(passwordUtil.encrypt(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User existingUser = memoryRepository.findByEmail(user.getEmail());

        if (existingUser == null) {
            existingUser = jdbcRepository.findByEmail(user.getEmail());
        }

        if (existingUser != null) {
            throw new AuthenticationException("Email already exists.");
        }

        boolean memorySaved = memoryRepository.save(user);
        boolean jdbcSaved = jdbcRepository.save(user);

        return memorySaved && jdbcSaved;
    }

    // Authenticates the user
    public User login(final User user) {

        if (user == null) {

            throw new NullPointerException("User cannot be null.");
        }

        String email = stringUtil.cleanEmail(user.getEmail());
        User existingUser = memoryRepository.findByEmail(email);

        if (existingUser == null) {
            existingUser = jdbcRepository.findByEmail(email);
        }

        if (existingUser == null) {
            throw new AuthenticationException("User not found.");
        }

        boolean isPasswordValid = passwordUtil.matches(
                        user.getPassword(),
                        existingUser.getPassword());

        if (!isPasswordValid) {
            throw new AuthenticationException("Invalid password.");
        }

        existingUser.setLastLoginAt(LocalDateTime.now());
        existingUser.setUpdatedAt(LocalDateTime.now());

        memoryRepository.update(existingUser);
        jdbcRepository.update(existingUser);

        return existingUser;
    }

    // Retrieves a user using an email address
    public User getUserByEmail(final String email) {

        String cleanEmail = stringUtil.cleanEmail(email);
        User user = memoryRepository.findByEmail(cleanEmail);

        if (user == null) {
            user = jdbcRepository.findByEmail(cleanEmail);
        }
        return user;
    }

    // Retrieves all users
    public Collection<User> findAll() {

        return jdbcRepository.findAll();
    }

    // Updates user information
    public boolean update(final User user) {

        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }

        user.setUpdatedAt(LocalDateTime.now());

        return memoryRepository.update(user) && jdbcRepository.update(user);
    }

    // Deletes a user
    public boolean delete(final Integer userId) {

        User user = jdbcRepository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException("User not found.");
        }
        return memoryRepository.delete(userId) && jdbcRepository.delete(userId);
    }
}