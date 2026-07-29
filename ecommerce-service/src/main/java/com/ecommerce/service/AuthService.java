/*
 * AuthService.java
 *
 * Version 1.3
 *
 * July 25, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.service;

import com.ecommerce.common.exception.AuthenticationException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.common.util.PasswordUtil;
import com.ecommerce.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for user authentication operations.
 *
 * Handles user registration, login validation, and user retrieval
 * using email authentication.
 *
 * This service works with both in-memory and JDBC repositories
 * to maintain user data consistency.
 */
@Service
public class  AuthService {

    private final UserRepository memoryRepository;
    private final UserRepository jdbcRepository;
    private final PasswordUtil passwordUtil;
    private final StringUtil stringUtil;

    /**
     * Creates AuthService with required dependencies.
     *
     * @param memoryRepository in-memory user repository
     * @param jdbcRepository JDBC user repository
     * @param passwordUtil password utility service
     * @param stringUtil string utility service
     */
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

    /**
     * Registers a new user.
     *
     * Validates user details, cleans input data, encrypts password,
     * checks existing users, and saves the user into repositories.
     *
     * @param user user object containing registration details
     * @return true if user is successfully saved, otherwise false
     * @throws NullPointerException if user is null
     * @throws AuthenticationException if email already exists
     */
    public boolean signup(final User user) {

        if (user == null) {
            throw new NullPointerException("User cannot be null.");
        }

        user.setName(stringUtil.clean(user.getName()));
        user.setEmail(stringUtil.cleanEmail(user.getEmail()));
        user.setPassword(passwordUtil.hash(user.getPassword()));
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

    /**
     * Authenticates a user using email and password.
     *
     * Validates user credentials and returns authenticated user details.
     *
     * @param user user object containing login credentials
     * @return authenticated User object
     * @throws NullPointerException if user is null
     * @throws AuthenticationException if user does not exist
     * or password is invalid
     */
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

        String enteredPassword = passwordUtil.hash(user.getPassword());

        if (!existingUser.getPassword().equals(enteredPassword)) {
            throw new AuthenticationException("Invalid password.");
        }

        return existingUser;
    }

    /**
     * Retrieves user details using email address.
     *
     * Searches user information from memory repository first,
     * then JDBC repository if user is not found.
     *
     * @param email user email address
     * @return matching User object, otherwise null
     * @throws NullPointerException if email is null
     */
    public User getUserByEmail(final String email) {

        if (email == null) {
            throw new NullPointerException("Email cannot be null.");
        }

        String cleanEmail = stringUtil.cleanEmail(email);
        User user = memoryRepository.findByEmail(cleanEmail);

        if (user == null) {
            user = jdbcRepository.findByEmail(cleanEmail);
        }

        return user;
    }
}