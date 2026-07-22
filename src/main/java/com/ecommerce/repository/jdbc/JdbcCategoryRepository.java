package com.ecommerce.repository.jdbc;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.util.DbConnection;
import com.ecommerce.util.LoggerUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class JdbcCategoryRepository implements CategoryRepository {

    @Override
    public boolean save(Category category) {

        if (category == null) {
            return false;
        }

        String sql = """
                INSERT INTO category(name)
                VALUES(?)
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getCategoryName());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return false;
    }

    @Override
    public Category findById(int categoryId) {

        String sql = """
                SELECT *
                FROM category
                WHERE category_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                Category category = new Category();

                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("name"));

                return category;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return null;
    }

    @Override
    public Category findByName(String categoryName) {

        String sql = """
                SELECT *
                FROM category
                WHERE name = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, categoryName);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                Category category = new Category();

                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("name"));

                return category;
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return null;
    }

    @Override
    public Collection<Category> findAll() {

        Collection<Category> categories = new ArrayList<>();

        String sql = """
                SELECT *
                FROM category
                ORDER BY category_id
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                Category category = new Category();

                category.setCategoryId(rs.getInt("category_id"));
                category.setCategoryName(rs.getString("name"));

                categories.add(category);
            }

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return categories;
    }

    @Override
    public boolean update(Category category) {

        String sql = """
                UPDATE category
                SET name = ?
                WHERE category_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getCategoryName());
            statement.setInt(2, category.getCategoryId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(int categoryId) {

        String sql = """
                DELETE FROM category
                WHERE category_id = ?
                """;

        try (Connection connection = DbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, categoryId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            LoggerUtil.getInstance().error(e.getMessage());
        }
        return false;
    }
}