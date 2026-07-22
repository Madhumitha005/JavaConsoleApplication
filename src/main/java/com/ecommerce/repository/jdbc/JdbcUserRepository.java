package com.ecommerce.repository.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.ecommerce.enumtype.Role;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.util.DbConnection;
import com.ecommerce.util.LoggerUtil;

public class JdbcUserRepository implements UserRepository {

    // Save user
    @Override
    public boolean save(User user) {

        if (user == null) {
            return false;
        }

        String sql = """
                INSERT INTO "user"
                (
                    name,
                    email,
                    password,
                    role
                )
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getRole().getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return false;
    }

    // Find user by email
    @Override
    public User findByEmail(String email) {

        String sql = """
                SELECT *
                FROM "user"
                WHERE email = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

                // Db convert integer role to Enum
                user.setRole(Role.fromId(resultSet.getInt("role")));

                return user;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return null;
    }

    // Get all users
    @Override
    public Collection<User> findAll() {

        Collection<User> users = new ArrayList<>();

        String sql = """
                SELECT *
                FROM "user"
                ORDER BY user_id
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                User user = new User();

                user.setId(resultSet.getInt("user_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.fromId(resultSet.getInt("role")));

                users.add(user);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return users;
    }
}