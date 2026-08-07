/*
 * JdbcCategoryRepository.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.category.repository.jdbc;

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.category.entity.Category;
import com.ecommerce.category.repository.CategoryRepository;

// JDBC implementation of CategoryRepository
@Repository("jdbcCategoryRepository")
public class JdbcCategoryRepository implements CategoryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCategoryRepository.class);
    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (resultSet, rowNum) -> {

                final Category category = new Category();

                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));
                category.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                category.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());

                return category;
            };

    // Creates a JdbcCategoryRepository object
    public JdbcCategoryRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    // Saves a category
    @Override
    public boolean save(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null.");

        final String sql = """
                INSERT INTO category
                (
                    category_name,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?)
                """;

        final int rows = jdbcTemplate.update(
                sql,
                category.getCategoryName(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );

        if (rows > 0) {

            LOGGER.info("Category saved successfully.");

            return true;
        }
        LOGGER.warn("Unable to save category.");

        return false;
    }

    // Updates a category
    @Override
    public boolean update(final Category category) {

        Objects.requireNonNull(category, "Category cannot be null.");

        final String sql = """
                UPDATE category
                SET 
                   category_name = ?,
                   updated_at = ?
                WHERE category_id = ?
                """;

        final int rows = jdbcTemplate.update(
                sql,
                category.getCategoryName(),
                category.getUpdatedAt(),
                category.getCategoryId()
        );

        if (rows > 0) {

            LOGGER.info("Category updated successfully.");

            return true;
        }
        LOGGER.warn("Unable to update category.");

        return false;
    }

    // Deletes a category
    @Override
    public boolean delete(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        final String sql = """
                DELETE FROM category
                WHERE category_id = ?
                """;

        final int rows = jdbcTemplate.update(
                sql,
                categoryId
        );

        if (rows > 0) {

            LOGGER.info("Category deleted successfully.");

            return true;
        }
        LOGGER.warn("Unable to delete category.");

        return false;
    }

    // Finds a category using its id
    @Override
    public Category findById(final Integer categoryId) {

        Objects.requireNonNull(categoryId, "Category ID cannot be null.");

        final String sql = """
                SELECT
                    category_id,
                    category_name,
                    created_at,
                    updated_at
                FROM category
                WHERE category_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        CATEGORY_ROW_MAPPER,
                        categoryId
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    // Finds a category using its name
    @Override
    public Category findByName(final String categoryName) {

        Objects.requireNonNull(categoryName, "Category name cannot be null.");

        final String sql = """
                SELECT
                    category_id,
                    category_name,
                    created_at,
                    updated_at
                FROM category
                WHERE category_name = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        CATEGORY_ROW_MAPPER,
                        categoryName
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    // Returns all categories
    @Override
    public Collection<Category> findAll() {

        final String sql = """
                SELECT
                    category_id,
                    category_name,
                    created_at,
                    updated_at
                FROM category
                ORDER BY category_id
                """;

        return jdbcTemplate.query(
                sql,
                CATEGORY_ROW_MAPPER
        );
    }

    // Checks whether a category exists
    @Override
    public boolean existsByName(final String categoryName) {

        Objects.requireNonNull(categoryName, "Category name cannot be null.");

        final String sql = """
                SELECT COUNT(*)
                FROM category
                WHERE category_name = ?
                """;

        final Integer count = jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        categoryName
                );
        return count != null && count > 0;
    }
}