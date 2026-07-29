/*
 * UserRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import com.ecommerce.model.User;

/**
 * Repository interface for managing User entities.
 *
 * Defines CRUD operations and user search functionality
 * based on user identifier and email address.
 *
 * Implementations may store user data using different storage
 * mechanisms such as database or in-memory collections.
 */
public interface UserRepository {

    boolean save(final User user);

    boolean update(final User user);

    boolean delete(final int userId);

    User findById(final int userId);

    User findByEmail(final String email);

    boolean existsByEmail(final String email);
}