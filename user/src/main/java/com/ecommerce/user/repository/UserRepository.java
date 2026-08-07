/*
 * UserRepository.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.repository;

import java.util.Collection;

import com.ecommerce.user.entity.User;

// Repository interface for managing user
public interface UserRepository {

    // Saves a user
    boolean save(final User user);

    // Updates a user
    boolean update(final User user);

    // Deletes a user
    boolean delete(final Integer userId);

    // Retrieves a user by id
    User findById(final Integer userId);

    // Retrieves a user by email address
    User findByEmail(final String email);

    // Checks whether the email already exists
    boolean existsByEmail(final String email);

    // Retrieves all users
    Collection<User> findAll();
}