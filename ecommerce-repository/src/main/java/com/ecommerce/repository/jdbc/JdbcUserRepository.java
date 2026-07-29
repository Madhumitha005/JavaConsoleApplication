package com.ecommerce.repository.jdbc;

import com.ecommerce.common.enums.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("jdbcUserRepository")
public class JdbcUserRepository implements UserRepository {

    // JDBC Template
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcUserRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Row Mapper
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

    // Save User
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

    // Update User
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

    // Delete User By UserId
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

    // Find the User By Id
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

    // Find User By Email
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

    // Find the Email is already Exists
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