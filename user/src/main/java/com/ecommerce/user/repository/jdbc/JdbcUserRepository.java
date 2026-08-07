/*
 * JdbcUserRepository.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.user.repository.jdbc;

import java.util.Collection;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.common.enums.Role;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

// JDBC implementation of UserRepository
@Repository("jdbcUserRepository")
public class JdbcUserRepository
        implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    // Creates a JdbcUserRepository object
    public JdbcUserRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    // Maps database records to User objects
    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) -> {

                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.fromId(resultSet.getInt("role")));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                return user;
            };

    // Saves a user
    @Override
    public boolean save(final User user) {

        String sql = """
                INSERT INTO "user"
                (
                    name,
                    email,
                    password,
                    role
                )
                VALUES (?, ?, ?, ? )
                """;

        return jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId()
        ) > 0;
    }

    // Updates a user
    @Override
    public boolean update(final User user) {

        String sql = """
            UPDATE "user"
            SET
                name = ?,
                email = ?,
                password = ?,
                role = ?,
                updated_at = ?
            WHERE id = ?
            """;

        return jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId(),
                user.getUpdatedAt(),
                user.getId()
        ) > 0;
    }

    // Deletes a user
    @Override
    public boolean delete(final Integer userId) {

        String sql = """
                DELETE FROM "user"
                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,
                userId
        ) > 0;
    }

    // Finds a user by id
    @Override
    public User findById(final Integer userId) {

        String sql = """
                SELECT *
                FROM "user"
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                USER_ROW_MAPPER,
                userId
        ).stream().findFirst().orElse(null);
    }

    // Finds a user by email
    @Override
    public User findByEmail(final String email) {

        String sql = """
                SELECT *
                FROM "user"
                WHERE email = ?
                """;

        return jdbcTemplate.query(
                sql,
                USER_ROW_MAPPER,
                email
        ).stream().findFirst().orElse(null);
    }

    // Checks whether an email exists
    @Override
    public boolean existsByEmail(final String email) {

        String sql = """
                SELECT COUNT(*)
                FROM "user"
                WHERE email = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email
        );

        return count != null && count > 0;
    }

    // Returns all users
    @Override
    public Collection<User> findAll() {

        String sql = """
                SELECT *
                FROM "user"
                """;

        return jdbcTemplate.query(
                sql,
                USER_ROW_MAPPER
        );
    }
}