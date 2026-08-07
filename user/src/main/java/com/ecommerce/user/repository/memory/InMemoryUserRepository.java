/*
 * InMemoryUserRepository.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

/**
 * In-memory implementation of UserRepository.
 */
@Repository("inMemoryUserRepository")
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users;

    // Creates an InMemoryUserRepository object
    public InMemoryUserRepository() {

        users = new ArrayList<>();
    }

    @Override
    public boolean save(final User user) {

        Objects.requireNonNull(user, "User cannot be null.");

        if (existsByEmail(user.getEmail())) {
            return false;
        }

        user.setId(IdGenerator.getInstance().nextUserId());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return users.add(user);
    }

    @Override
    public boolean update(final User user) {

        Objects.requireNonNull(user, "User cannot be null.");

        User existingUser = findById(user.getId());

        if (existingUser == null) {
            return false;
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());
        existingUser.setUpdatedAt(LocalDateTime.now());

        return true;
    }

    @Override
    public boolean delete(final Integer userId) {

        if (userId == null) {
            return false;
        }

        return users.removeIf(user -> userId.equals(user.getId()));
    }

    @Override
    public User findById(final Integer userId) {

        if (userId == null) {
            return null;
        }

        for (User user : users) {

            if (userId.equals(user.getId())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findByEmail(final String email) {

        if (email == null) {
            return null;
        }

        for (User user : users) {

            if (user.getEmail() != null
                    && user.getEmail()
                    .equalsIgnoreCase(email)) {

                return user;
            }
        }
        return null;
    }

    @Override
    public boolean existsByEmail(final String email) {

        return findByEmail(email) != null;
    }

    @Override
    public Collection<User> findAll() {

        return new ArrayList<>(users);
    }
}