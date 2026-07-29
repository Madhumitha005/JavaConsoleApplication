/*
 * InMemoryUserRepository.java
 *
 * Version 1.2
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.memory;

import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.common.util.IdGenerator;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collection;

/**
 * In-memory implementation of UserRepository.
 *
 * This repository manages User objects using an in-memory collection.
 * The data is temporarily stored during application execution without
 * connecting to an external database.
 *
 * Provides user CRUD operations and email-based user search functionality.
 */
@Repository("inMemoryUserRepository")
public class InMemoryUserRepository implements UserRepository {

    // Stores user objects in memory
    private final Collection<User> users;

    // Stores user objects in memory
    public InMemoryUserRepository() {
        this.users = new ArrayList<>();
    }

    /**
     * Saves a new user into the in-memory collection.
     *
     * @param user user object to be saved
     * @return true if user is saved successfully, otherwise false
     */
    @Override
    public boolean save(final User user) {

        if (user == null) {
            return false;
        }

        if (existsByEmail(user.getEmail())) {
            return false;
        }

        user.setId(
                IdGenerator.getInstance().nextUserId()
        );

        return users.add(user);
    }

    /**
     * Updates an existing user using user id.
     *
     * @param user updated user object
     * @return true if user is updated successfully, otherwise false
     */
    @Override
    public boolean update(final User user) {

        if (user == null) {
            return false;
        }

        for (User existingUser : users) {

            if (existingUser.getId() == user.getId()) {

                existingUser.setName(user.getName());
                existingUser.setEmail(user.getEmail());
                existingUser.setPassword(user.getPassword());
                existingUser.setRole(user.getRole());

                return true;
            }
        }

        return false;
    }
    /**
     * Deletes a user using user id.
     *
     * @param userId unique identifier of user
     * @return true if user is deleted successfully, otherwise false
     */
    @Override
    public boolean delete(final int userId) {

        return users.removeIf(
                user -> user.getId() == userId
        );
    }

    /**
     * Finds a user using user id.
     *
     * @param userId unique identifier of user
     * @return matching User object, otherwise null
     */
    @Override
    public User findById(final int userId) {

        for (User user : users) {

            if (user.getId() == userId) {
                return user;
            }
        }

        return null;
    }

    /**
     * Finds a user using email address.
     *
     * @param email user email address
     * @return matching User object, otherwise null
     */
    @Override
    public User findByEmail(final String email) {

        if (email == null) {
            return null;
        }

        for (User user : users) {

            if (user.getEmail() != null
                    && user.getEmail().equalsIgnoreCase(email)) {

                return user;
            }
        }

        return null;
    }

    /**
     * Checks whether a user exists with the given email address.
     *
     * @param email user email address
     * @return true if user exists, otherwise false
     */
    @Override
    public boolean existsByEmail(final String email) {

        return findByEmail(email) != null;
    }
}