/*
 * JdbcCategoryRepository.java
 *
 * Version 1.6
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.jdbc;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

/**
 * JDBC implementation of the CategoryRepository interface.
 *
 * This class performs CRUD operations for categories
 * using Spring JdbcTemplate.
 */
@Repository("jdbcCategoryRepository")
public class JdbcCategoryRepository
        implements CategoryRepository {

    // Logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCategoryRepository.class);

    // Spring JdbcTemplate used for database operation
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcCategoryRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Maps a database row to a Category object
    private final RowMapper<Category> ROW_MAPPER = (resultSet, rowNum) -> {

                Category category = new Category();

                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));

                return category;
            };

    /**
     * Saves a category.
     *
     * @param category category to save
     * @return true if saved successfully
     */
    @Override
    public boolean save(final Category category) {

        final String sql = """
                INSERT INTO category
                (
                    category_name
                )
                VALUES (?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                category.getCategoryName()
        );

        if (rows > 0) {

            LOGGER.info("Category saved successfully.");

            return true;
        }

        LOGGER.warn("Category save failed.");

        return false;
    }

    /**
     * Updates a category.
     *
     * @param category updated category
     * @return true if updated successfully
     */
    @Override
    public boolean update(final Category category) {

        final String sql = """
                UPDATE category
                SET category_name = ?
                WHERE category_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                category.getCategoryName(),
                category.getCategoryId()
        );

        if (rows > 0) {

            LOGGER.info("Category updated successfully. " + "Category ID: {}", category.getCategoryId());

            return true;
        }

        LOGGER.warn("Category update failed. " + "Category ID: {}", category.getCategoryId());

        return false;
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId category ID
     * @return true if deleted successfully
     */
    @Override
    public boolean delete(final int categoryId) {

        final String sql = """
                DELETE FROM category
                WHERE category_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                categoryId
        );

        if (rows > 0) {

            LOGGER.info("Category deleted successfully. " + "Category ID: {}", categoryId);

            return true;
        }

        LOGGER.warn("Category delete failed. " + "Category ID: {}", categoryId);

        return false;
    }

    /**
     * Finds a category by its ID.
     *
     * @param categoryId category ID
     * @return matching category or null
     */
    @Override
    public Category findById(final int categoryId) {

        final String sql = """
                SELECT
                    category_id,
                    category_name
                FROM category
                WHERE category_id = ?
                """;

        List<Category> categories =
                jdbcTemplate.query(
                        sql,
                        ROW_MAPPER,
                        categoryId
                );

        return categories.isEmpty()
                ? null
                : categories.getFirst();
    }

    /**
     * Finds a category by its name.
     *
     * @param categoryName category name
     * @return matching category or null
     */
    @Override
    public Category findByName(final String categoryName) {

        final String sql = """
                SELECT
                    category_id,
                    category_name
                FROM category
                WHERE category_name = ?
                """;

        List<Category> categories =
                jdbcTemplate.query(
                        sql,
                        ROW_MAPPER,
                        categoryName
                );

        return categories.isEmpty() ? null : categories.getFirst();
    }

    /**
     * Returns all categories.
     *
     * @return collection of categories
     */
    @Override
    public Collection<Category> findAll() {

        final String sql = """
                SELECT
                    category_id,
                    category_name
                FROM category
                ORDER BY category_id
                """;

        return jdbcTemplate.query(
                sql,
                ROW_MAPPER
        );
    }

    /**
     * Checks whether a category name already exists.
     *
     * @param categoryName category name
     * @return true if the category exists
     */
    @Override
    public boolean existsByName(final String categoryName) {

        final String sql = """
                SELECT COUNT(*)
                FROM category
                WHERE category_name = ?
                """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        categoryName
                );

        return count != null && count > 0;
    }
}