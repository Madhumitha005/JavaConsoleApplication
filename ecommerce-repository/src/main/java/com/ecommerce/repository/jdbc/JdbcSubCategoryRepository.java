package com.ecommerce.repository.jdbc;

import com.ecommerce.model.Category;
import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.SubCategoryRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("jdbcSubCategoryRepository")
public class JdbcSubCategoryRepository
        implements SubCategoryRepository {

    // JDBC Template
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcSubCategoryRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Row Mapper
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

    // Add to Sub Category
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

    // Update Sub Category
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

    // Delete Sub Category
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

    // Find Sub Category By Id
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

    // Find category by Name
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

    // Find SubCategory By Ctegory Id
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

    // Find All Sub Categories
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

    // Check Sub Category name exists
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