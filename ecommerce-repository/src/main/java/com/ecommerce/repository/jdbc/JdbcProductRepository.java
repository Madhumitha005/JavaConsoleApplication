package com.ecommerce.repository.jdbc;

import java.util.Collection;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.model.SubCategory;
import com.ecommerce.repository.ProductRepository;

@Repository("jdbcProductRepository")
public class JdbcProductRepository
        implements ProductRepository {

    // JDBC Template
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcProductRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JDBCTemplate cannot be null.");
    }

    // Product Row Mapper
    private static final RowMapper<Product> PRODUCT_ROW_MAPPER = (resultSet, rowNum) -> {

               // Category
                Category category = new Category();

                category.setCategoryId(resultSet.getInt("category_id"));
                category.setCategoryName(resultSet.getString("category_name"));

                // Sub Category
                SubCategory subCategory = new SubCategory();

                subCategory.setSubCategoryId(resultSet.getInt("subcategory_id"));
                subCategory.setSubCategoryName(resultSet.getString("subcategory_name"));
                subCategory.setCategory(category);

                //Product
                Product product = new Product();

                product.setProductId(resultSet.getInt("product_id"));
                product.setProductName(resultSet.getString("product_name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDiscountPercentage(resultSet.getDouble("discount_percentage"));

                // Database stock_quantity
                product.setQuantity(resultSet.getInt("stock_quantity"));
                product.setCategory(category);
                product.setSubCategory(subCategory);

                //Product Status
                product.setProductStatus(ProductStatus.fromId(resultSet.getInt("status_id")));
                return product;
            };

    //Save Product
    @Override
    public boolean save(final Product product) {

        if (product == null) {
            return false;
        }

        if (product.getCategory() == null) {
            return false;
        }

        if (product.getSubCategory() == null) {
            return false;
        }

        if (product.getProductStatus() == null) {
            return false;
        }

        if (product.getDiscountPercentage() < 0 || product.getDiscountPercentage() > 100) {
            return false;
        }

        String sql = """
                INSERT INTO product
                (
                    product_name,
                    price,
                    discount_percentage,
                    stock_quantity,
                    category_id,
                    subcategory_id,
                    status_id
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
      int rowsAffected =
                jdbcTemplate.update(
                        sql,
                        product.getProductName(),
                        product.getPrice(),
                        product.getDiscountPercentage(),
                        product.getQuantity(),
                        product.getCategory().getCategoryId(),
                        product.getSubCategory().getSubCategoryId(),
                        product.getProductStatus().getId()
                );

        return rowsAffected > 0;
    }

    // Update Product
    @Override
    public boolean update(final Product product) {

        if (product == null) {
            return false;
        }

        if (product.getProductId() <= 0) {
            return false;
        }

        if (product.getCategory() == null) {
            return false;
        }

        if (product.getSubCategory() == null) {
            return false;
        }

        if (product.getProductStatus() == null) {
            return false;
        }

        if (product.getDiscountPercentage() < 0 || product.getDiscountPercentage() > 100) {
            return false;
        }

        String sql = """
                UPDATE product
                SET
                    product_name = ?,
                    price = ?,
                    discount_percentage = ?,
                    stock_quantity = ?,
                    category_id = ?,
                    subcategory_id = ?,
                    status_id = ?
                WHERE product_id = ?
                """;

        int rowsAffected =
                jdbcTemplate.update(
                        sql,
                        product.getProductName(),
                        product.getPrice(),
                        product.getDiscountPercentage(),
                        product.getQuantity(),
                        product.getCategory().getCategoryId(),
                        product.getSubCategory().getSubCategoryId(),
                        product.getProductStatus().getId(),
                        product.getProductId()
                );

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(final int productId) {

        if (productId <= 0) {
            return false;
        }

        String sql = """
                DELETE FROM product
                WHERE product_id = ?
                """;

        int rowsAffected =
                jdbcTemplate.update(
                        sql,
                        productId
                );

        return rowsAffected > 0;
    }

    @Override
    public Product findById(
            final int productId) {

        if (productId <= 0) {
            return null;
        }

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.price,
                    p.discount_percentage,
                    p.stock_quantity,
                    p.category_id,
                    c.category_name,
                    p.subcategory_id,
                    sc.subcategory_name,
                    p.status_id
                FROM product p
                INNER JOIN category c
                    ON p.category_id = c.category_id
                INNER JOIN subcategory sc
                    ON p.subcategory_id = sc.subcategory_id
                WHERE p.product_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        PRODUCT_ROW_MAPPER,
                        productId
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Product findByName(final String productName) {

        if (productName == null
                || productName.isBlank()) {

            return null;
        }

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.price,
                    p.discount_percentage,
                    p.stock_quantity,
                    p.category_id,
                    c.category_name,
                    p.subcategory_id,
                    sc.subcategory_name,
                    p.status_id
                FROM product p
                INNER JOIN category c
                    ON p.category_id = c.category_id
                INNER JOIN subcategory sc
                    ON p.subcategory_id = sc.subcategory_id
                WHERE p.product_name = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        PRODUCT_ROW_MAPPER,
                        productName
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Product> findAll() {

        String sql = """
                SELECT
                    p.product_id,
                    p.product_name,
                    p.price,
                    p.discount_Percentage,
                    p.stock_quantity,
                    p.category_id,
                    c.category_name,
                    p.subcategory_id,
                    sc.subcategory_name,
                    p.status_id
                FROM product p
                INNER JOIN category c
                    ON p.category_id = c.category_id
                INNER JOIN subcategory sc
                    ON p.subcategory_id = sc.subcategory_id
                ORDER BY p.product_id
                """;

        return jdbcTemplate.query(
                sql,
                PRODUCT_ROW_MAPPER
        );
    }

    @Override
    public boolean existsByName(final String productName) {

        if (productName == null || productName.isBlank()) {

            return false;
        }

        String sql = """
                SELECT COUNT(*)
                FROM product
                WHERE product_name = ?
                """;

        Integer count =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        productName
                );

        return count != null
                && count > 0;
    }
}
