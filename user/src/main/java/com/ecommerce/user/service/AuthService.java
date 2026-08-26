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

import java.util.Collection;
import java.util.Objects;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ecommerce.common.exception.AuthenticationException;
import com.ecommerce.common.exception.ResourceNotFoundException;
import com.ecommerce.common.util.PasswordUtil;
import com.ecommerce.common.util.StringUtil;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordUtil passwordUtil;
    private final StringUtil stringUtil;

    public AuthService(
            final UserRepository repository,
            final PasswordUtil passwordUtil,
            final StringUtil stringUtil) {

        this.repository = Objects.requireNonNull(
                repository,
                "UserRepository cannot be null.");

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

        // Clean user input
        user.setName(
                stringUtil.clean(user.getName()));

        user.setEmail(
                stringUtil.cleanEmail(user.getEmail()));

        // Check duplicate email
        if (repository.existsByEmail(user.getEmail())) {
            throw new AuthenticationException(
                    "Email already exists.");
        }

        // Encrypt password before saving
        user.setPassword(
                passwordUtil.encrypt(user.getPassword()));

        /*
         * createdAt and updatedAt are handled by
         * @CreationTimestamp and @UpdateTimestamp
         * in User entity.
         */

        return repository.save(user);
    }

    // Authenticates the user
    public User login(final User user) {

        Objects.requireNonNull(
                user,
                "User cannot be null.");

        final String email =
                stringUtil.cleanEmail(user.getEmail());

        final User existingUser =
                repository.findByEmail(email);

        if (existingUser == null) {
            throw new AuthenticationException(
                    "User not found.");
        }

        final boolean isPasswordValid =
                passwordUtil.matches(
                        user.getPassword(),
                        existingUser.getPassword());

        if (!isPasswordValid) {
            throw new AuthenticationException(
                    "Invalid password.");
        }

        return existingUser;
    }

    // Retrieves a user using email
    @Cacheable(
            value = "users",
            key = "'email:' + #email")
    public User getUserByEmail(final String email) {

        final String cleanEmail =
                stringUtil.cleanEmail(email);

        return repository.findByEmail(cleanEmail);
    }

    // Retrieves all users
    public Collection<User> findAll() {

        return repository.findAll();
    }

    // Retrieves user by id
    @Cacheable(
            value = "users",
            key = "'id:' + #userId")
    public User findById(final Integer userId) {

        Objects.requireNonNull(
                userId,
                "User ID cannot be null.");

        return repository.findById(userId);
    }

    // Updates user information
    @CachePut(
            value = "users",
            key = "'id:' + #user.id")
    public boolean update(final User user) {

        Objects.requireNonNull(
                user,
                "User cannot be null.");

        return repository.update(user);
    }

    // Deletes a user
    @CacheEvict(
            value = "users",
            key = "'id:' + #userId")
    public boolean delete(final Integer userId) {

        Objects.requireNonNull(
                userId,
                "User ID cannot be null.");

        final User user =
                repository.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User not found.");
        }

        return repository.delete(userId);
    }
}