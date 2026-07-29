/*
 * JdbcSubCategoryRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */

package com.ecommerce.repository.jdbc;

import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.SubCategoryRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * JDBC implementation of SubCategoryRepository.
 *
 * This class handles database operations related to
 * sub category management using JdbcTemplate.
 */
@Repository("jdbcSubCategoryRepository")
public class JdbcSubCategoryRepository
        implements SubCategoryRepository {

    // JDBC Template used for Db Operation
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcSubCategoryRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Maps a database row to a Product object
    private static final RowMapper<SubCategory> SUB_CATEGORY_ROW_MAPPER = (resultSet, rowNum) -> {

                // Create Category Object
                Category category = new Category();

                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));

                // Create SubCategory Object
                SubCategory subCategory = new SubCategory();
                subCategory.setSubCategoryId(resultSet.getInt("subcategory_id"));
                subCategory.setSubCategoryName(resultSet.getString("subcategory_name"));
                subCategory.setCategory(category);

                return subCategory;
            };

    /**
     * Saves a new sub category.
     *
     * @param subCategory sub category object
     * @return true if saved successfully
     */
    @Override
    public boolean save(final SubCategory subCategory) {

        String sql = """
                INSERT INTO subcategory
                (
                    subcategory_name,
                    category_id
                )
                VALUES (?, ?)
                """;

        return jdbcTemplate.update(
                sql,
                subCategory.getSubCategoryName(),
                subCategory.getCategory().getCategoryId()
        ) > 0;
    }

    /**
     * Updates existing sub category.
     *
     * @param subCategory sub category object
     * @return true if updated successfully
     */
    @Override
    public boolean update(final SubCategory subCategory) {

        String sql = """
                UPDATE subcategory
                SET
                    subcategory_name = ?,
                    category_id = ?
                WHERE subcategory_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                subCategory.getSubCategoryName(),
                subCategory.getCategory().getCategoryId(),
                subCategory.getSubCategoryId()
        ) > 0;
    }

    /**
     * Deletes sub category by id.
     *
     * @param subCategoryId sub category id
     * @return true if deleted successfully
     */
    @Override
    public boolean delete(final int subCategoryId) {

        String sql = """
                DELETE FROM subcategory
                WHERE subcategory_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                subCategoryId
        ) > 0;
    }

    /**
     * Finds sub category using id.
     *
     * @param subCategoryId sub category id
     * @return sub category object
     */
    @Override
    public SubCategory findById(final int subCategoryId) {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name

                FROM subcategory sc

                JOIN category c
                    ON sc.category_id =
                       c.category_id

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

    /**
     * Finds sub category using name.
     *
     * @param subCategoryName sub category name
     * @return sub category object
     */
    @Override
    public SubCategory findByName(final String subCategoryName) {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name

                FROM subcategory sc

                JOIN category c
                    ON sc.category_id =
                       c.category_id

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

    /**
     * Finds sub categories by category id.
     *
     * @param categoryId category id
     * @return collection of sub categories
     */
    @Override
    public Collection<SubCategory> findByCategoryId(final int categoryId) {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name

                FROM subcategory sc

                JOIN category c
                    ON sc.category_id =
                       c.category_id

                WHERE sc.category_id = ?

                ORDER BY sc.subcategory_id
                """;

        return jdbcTemplate.query(
                sql,
                SUB_CATEGORY_ROW_MAPPER,
                categoryId
        );
    }

    /**
     * Finds all sub categories.
     *
     * @return collection of sub categories
     */
    @Override
    public Collection<SubCategory> findAll() {

        String sql = """
                SELECT
                    sc.subcategory_id,
                    sc.subcategory_name,
                    sc.category_id,
                    c.category_name

                FROM subcategory sc

                JOIN category c
                    ON sc.category_id =
                       c.category_id

                ORDER BY sc.subcategory_id
                """;

        return jdbcTemplate.query(
                sql,
                SUB_CATEGORY_ROW_MAPPER
        );
    }

    /**
     * Checks whether sub category name exists.
     *
     * @param subCategoryName sub category name
     * @return true if exists
     */
    @Override
    public boolean existsByName(
            final String subCategoryName) {

        String sql = """
                SELECT COUNT(*)
                FROM subcategory
                WHERE subcategory_name = ?
                """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        subCategoryName
                );

        return count != null
                && count > 0;
    }
}