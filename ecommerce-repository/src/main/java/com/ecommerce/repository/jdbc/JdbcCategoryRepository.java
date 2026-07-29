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

@Repository("jdbcCategoryRepository")
public class JdbcCategoryRepository
        implements CategoryRepository {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCategoryRepository.class);

    // JDBC Template
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcCategoryRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Row Mapper
    private final RowMapper<Category> ROW_MAPPER = (resultSet, rowNum) -> {

                Category category = new Category();

                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));

                return category;
            };

    // Save Category
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

    // Update Category
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

    // Delete Category
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

    // Find Category By Id
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

    // Find category by name
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

    // Find All Categories
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

    // Check category name exists
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