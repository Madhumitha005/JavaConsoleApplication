/*
 * JdbcSubCategoryRepository.java
 *
 * Version 1.0
 *
 * July 31, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.subcategory.repository.jdbc;

import java.util.Collection;
import java.util.Objects;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.category.entity.Category;
import com.ecommerce.subcategory.entity.SubCategory;
import com.ecommerce.subcategory.repository.SubCategoryRepository;

/**
 * JDBC implementation of SubCategoryRepository.
 *
 * This repository performs CRUD operations
 * using JdbcTemplate.
 */
@Repository("jdbcSubCategoryRepository")
public class JdbcSubCategoryRepository
        implements SubCategoryRepository {

    // Logger object
    private static final Logger LOGGER =
            LoggerFactory.getLogger(JdbcSubCategoryRepository.class);

    // JdbcTemplate object
    private final JdbcTemplate jdbcTemplate;

    // Row mapper
    private static final RowMapper<SubCategory> SUB_CATEGORY_ROW_MAPPER = (resultSet, rowNum) -> {

                Category category = new Category();
                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));

                SubCategory subCategory = new SubCategory();
                subCategory.setSubCategoryId(resultSet.getInt("subcategory_id"));
                subCategory.setSubCategoryName(resultSet.getString("subcategory_name"));
                subCategory.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                subCategory.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                subCategory.setCategory(category);

                return subCategory;
            };

    // Creates a JdbcSubCategoryRepository object
    public JdbcSubCategoryRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    // Saves a subcategory
    @Override
    public boolean save(final SubCategory subCategory) {

        String sql = """
                INSERT INTO sub_category
                (
                    subcategory_name,
                    category_id,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                subCategory.getSubCategoryName(),
                subCategory.getCategory().getCategoryId(),
                subCategory.getCreatedAt(),
                subCategory.getUpdatedAt()
        );

        return rows > 0;
    }

    // Updates a subcategory
    @Override
    public boolean update(final SubCategory subCategory) {

        String sql = """
                UPDATE sub_category
                SET
                    subcategory_name = ?,
                    category_id = ?,
                    updated_at = ?
                WHERE subcategory_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                subCategory.getSubCategoryName(),
                subCategory.getCategory().getCategoryId(),
                subCategory.getUpdatedAt(),
                subCategory.getSubCategoryId()
        );

        return rows > 0;
    }

    // Deletes a subcategory
    @Override
    public boolean delete(final Integer subCategoryId) {

        String sql = """
                DELETE FROM sub_category
                WHERE subcategory_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                subCategoryId
        );
        return rows > 0;
    }

    // Finds a subcategory by id
    @Override
    public SubCategory findById(final Integer subCategoryId) {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name,
                    sc.created_at,
                    sc.updated_at
                FROM sub_category sc
                JOIN category c
                ON sc.category_id = c.category_id
                WHERE sc.subcategory_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        SUB_CATEGORY_ROW_MAPPER,
                        subCategoryId
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    // Finds a subcategory by name
    @Override
    public SubCategory findByName(final String subCategoryName) {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name,
                    sc.created_at,
                    sc.updated_at
                FROM sub_category sc
                JOIN category c
                ON sc.category_id = c.category_id
                WHERE sc.subcategory_name = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        SUB_CATEGORY_ROW_MAPPER,
                        subCategoryName
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    // Finds subcategories using category id
    @Override
    public Collection<SubCategory> findByCategoryId(final Integer categoryId) {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name,
                    sc.created_at,
                    sc.updated_at
                FROM sub_category sc
                JOIN category c
                ON sc.category_id = c.category_id
                WHERE sc.category_id = ?
                ORDER BY sc.subcategory_id
                """;

        return jdbcTemplate.query(
                sql,
                SUB_CATEGORY_ROW_MAPPER,
                categoryId
        );
    }

    // Returns all subcategories
    @Override
    public Collection<SubCategory> findAll() {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name,
                    sc.created_at,
                    sc.updated_at
                FROM sub_category sc
                JOIN category c
                ON sc.category_id = c.category_id
                ORDER BY sc.subcategory_id
                """;

        return jdbcTemplate.query(
                sql,
                SUB_CATEGORY_ROW_MAPPER
        );
    }

    // Checks whether a subcategory exists
    @Override
    public boolean existsByName(final String subCategoryName) {

        String sql = """
                SELECT COUNT(*)
                FROM sub_category
                WHERE subcategory_name = ?
                """;

        Integer count = jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        subCategoryName
                );
        return count != null && count > 0;
    }
}