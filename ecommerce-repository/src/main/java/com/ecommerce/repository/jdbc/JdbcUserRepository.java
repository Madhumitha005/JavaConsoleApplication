/*
 * JdbcUserRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.jdbc;

import com.ecommerce.common.enums.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
/**
 * JDBC implementation of UserRepository.
 *
 * This class handles all database operations
 * related to User entity using JdbcTemplate.
 **/
@Repository("jdbcUserRepository")
public class JdbcUserRepository implements UserRepository {

    // JDBC Template used for Db Operation
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcUserRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Maps a database row to a Product object
    private static final RowMapper<User> USER_ROW_MAPPER = (resultSet, rowNum) -> {

                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(
                        Role.fromId(resultSet.getInt("role_id"))
                );

                return user;
            };

    /*
     * Save new user into database.
     *
     * @param user User object
     * @return true if inserted successfully
     */
    @Override
    public boolean save(final User user) {

        String sql = """
                INSERT INTO "user"
                (
                    name,
                    email,
                    password,
                    role_id
                )
                VALUES (?, ?, ?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId()
        ) > 0;
    }

    /*
     * Update existing user details.
     *
     * @param user User object
     * @return true if update successful
     */
    @Override
    public boolean update(final User user) {

        String sql = """
                UPDATE "user"
                SET name=?,
                    email=?,
                    password=?,
                    role_id=?
                WHERE user_id=?
                """;

        return jdbcTemplate.update(
                sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getId(),
                user.getId()
        ) > 0;
    }

    /*
     * Delete user using user id.
     *
     * @param userId User primary key
     * @return true if deleted successfully
     */
    @Override
    public boolean delete(final int userId) {

        String sql = """
                DELETE FROM "user"
                WHERE user_id=?
                """;

        return jdbcTemplate.update(
                sql,
                userId
        ) > 0;
    }

    /*
     * Find user by primary key.
     *
     * @param userId User ID
     * @return User object if found
     */
    @Override
    public User findById(final int userId) {

        String sql = """
                SELECT *
                FROM "user"
                WHERE user_id=?
                """;

        return jdbcTemplate.query(
                sql,
                USER_ROW_MAPPER,
                userId
        ).stream().findFirst().orElse(null);
    }

    /*
     * Find user using email address.
     *
     * @param email User email
     * @return User object if found
     */
    @Override
    public User findByEmail(final String email) {

        String sql = """
                SELECT *
                FROM "user"
                WHERE email=?
                """;

        return jdbcTemplate.query(
                sql,
                USER_ROW_MAPPER,
                email
        ).stream().findFirst().orElse(null);
    }

    /*
     * Checks whether email already exists.
     *
     * @param email User email
     * @return true if email exists
     */
    @Override
    public boolean existsByEmail(final String email) {

        String sql = """
                SELECT COUNT(*)
                FROM "user"
                WHERE email=?
                """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                email
        );

        return count != null && count > 0;
    }
}