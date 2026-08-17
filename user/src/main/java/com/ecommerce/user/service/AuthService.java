/*
 * AuthService.java
 *
 * Version 2.0
 *
 * August 14, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.ecommerce.common.exception.AuthenticationException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.util.PasswordUtil;
import com.ecommerce.common.util.StringUtil;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository jdbcRepository;
    private final PasswordUtil passwordUtil;
    private final StringUtil stringUtil;

    public AuthService(

            @Qualifier("jdbcUserRepository")
            final UserRepository jdbcRepository,

            final PasswordUtil passwordUtil,
            final StringUtil stringUtil) {

        this.jdbcRepository = Objects.requireNonNull(
                jdbcRepository,
                "JDBC repository cannot be null.");

        this.passwordUtil = Objects.requireNonNull(
                passwordUtil,
                "PasswordUtil cannot be null.");

        this.stringUtil = Objects.requireNonNull(
                stringUtil,
                "StringUtil cannot be null.");
    }

    // Registers a new user
    public boolean signup(final User user) {

        Objects.requireNonNull(
                user,
                "User cannot be null.");

        user.setName(
                stringUtil.clean(user.getName()));

        user.setEmail(
                stringUtil.cleanEmail(user.getEmail()));

        User existingUser =
                jdbcRepository.findByEmail(
                        user.getEmail());

        if (existingUser != null) {

            throw new AuthenticationException(
                    "Email already exists.");
        }

        user.setPassword(
                passwordUtil.encrypt(
                        user.getPassword()));

        user.setCreatedAt(
                LocalDateTime.now());

        user.setUpdatedAt(
                LocalDateTime.now());

        return jdbcRepository.save(user);
    }

    // Authenticates the user
    public User login(final User user) {

        Objects.requireNonNull(
                user,
                "User cannot be null.");

        String email =
                stringUtil.cleanEmail(
                        user.getEmail());

        User existingUser =
                jdbcRepository.findByEmail(email);

        if (existingUser == null) {

            throw new AuthenticationException(
                    "User not found.");
        }

        boolean isPasswordValid =
                passwordUtil.matches(
                        user.getPassword(),
                        existingUser.getPassword());

        if (!isPasswordValid) {

            throw new AuthenticationException(
                    "Invalid password.");
        }

        existingUser.setLastLoginAt(
                LocalDateTime.now());

        existingUser.setUpdatedAt(
                LocalDateTime.now());

        jdbcRepository.update(existingUser);

        return existingUser;
    }

    // Retrieves a user using an email address
    @Cacheable(
            value = "users",
            key = "#email")
    public User getUserByEmail(final String email) {

        String cleanEmail = stringUtil.cleanEmail(email);

        return jdbcRepository.findByEmail(cleanEmail);
    }
    // Retrieves all users
    public Collection<User> findAll() {

        return jdbcRepository.findAll();
    }

    @Cacheable(
            value = "users",
            key = "#userId")
    public User findById(
            final Integer userId) {

        Objects.requireNonNull(
                userId,
                "User ID cannot be null.");

        return jdbcRepository.findById(userId);
    }

    // Updates user information
    @CachePut(
            value = "users",
            key = "#user.id")
    public boolean update(final User user) {

        Objects.requireNonNull(user, "User cannot be null.");

        user.setUpdatedAt(LocalDateTime.now());

        return jdbcRepository.update(user);
    }

    // Deletes a user
    @CacheEvict(
            value = "users",
            key = "#userId")
    public boolean delete(
            final Integer userId) {

        Objects.requireNonNull(
                userId,
                "User ID cannot be null.");

        User user =
                jdbcRepository.findById(userId);

        if (user == null) {

            throw new ResourceNotFoundException(
                    "User not found.");
        }

        return jdbcRepository.delete(userId);
    }
}