package com.ecommerce.product.repository.jdbc;

import java.util.Collection;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.user.entity.User;
import com.ecommerce.common.enums.ProductStatus;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.subcategory.entity.SubCategory;

@Repository("jdbcProductRepository")
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Product> PRODUCT_ROW_MAPPER =
            (rs, rowNum) -> {

                Product product = new Product();

                SubCategory subCategory = new SubCategory();
                subCategory.setSubCategoryId(rs.getInt("subcategory_id"));

                User seller = new User();
                seller.setId(rs.getInt("seller_id"));

                product.setProductId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                product.setDiscount(rs.getDouble("discount"));
                product.setTax(rs.getDouble("tax"));
                product.setStatusId(rs.getInt("status_id"));
                product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                product.setSeller(seller);
                product.setSubCategory(subCategory);

                return product;
            };

    @Override
    public boolean save(final Product product) {

        String sql = """
                INSERT INTO product
                (
                    seller_id,
                    name,
                    price,
                    quantity,
                    discount,
                    tax,
                    subcategory_id,
                    status_id,
                    created_at,
                    updated_at
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                product.getSeller().getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDiscount(),
                product.getTax(),
                product.getSubCategory().getSubCategoryId(),
                product.getStatusId(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
        return rows > 0;
    }

    @Override
    public Product findById(final Integer productId) {

        String sql = """
                SELECT *
                FROM product
                WHERE id = ?
                """;

        return jdbcTemplate.query(
                sql,
                PRODUCT_ROW_MAPPER,
                productId
        ).stream().findFirst().orElse(null);
    }

    @Override
    public Collection<Product> findAll() {

        String sql = """
                SELECT *
                FROM product
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                PRODUCT_ROW_MAPPER
        );
    }

    @Override
    public boolean existsByName(
            final String productName,
            final Integer sellerId) {

        String sql = """
            SELECT COUNT(*)
            FROM product
            WHERE LOWER(name) = LOWER(?)
            AND seller_id = ?
            """;

        Integer count = jdbcTemplate.queryForObject(
                sql,
                Integer.class,
                productName,
                sellerId
        );
        return count != null && count > 0;
    }

    @Override
    public Product findByName(final String name) {

        String sql = """
            SELECT *
            FROM product
            WHERE name = ?
            """;

        return jdbcTemplate.query(
                        sql,
                        PRODUCT_ROW_MAPPER,
                        name
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(final Product product) {

        String sql = """
            UPDATE product
            SET
                seller_id = ?,
                name = ?,
                price = ?,
                quantity = ?,
                discount = ?,
                tax = ?,
                subcategory_id = ?,
                status_id = ?,
                updated_at = ?
            WHERE id = ?
            """;

        int rows = jdbcTemplate.update(
                sql,
                product.getSeller().getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDiscount(),
                product.getTax(),
                product.getSubCategory().getSubCategoryId(),
                product.getStatusId(),
                product.getUpdatedAt(),
                product.getProductId()
        );
        return rows > 0;
    }

    @Override
    public boolean delete(final Integer productId) {

        String sql = """
            DELETE FROM product
            WHERE id = ?
            """;

        int rows = jdbcTemplate.update(
                sql,
                productId
        );
        return rows > 0;
    }

    @Override
    public Collection<Product> findBySellerId(final Integer sellerId) {

        String sql = """
            SELECT *
            FROM product
            WHERE seller_id = ?
            """;

        return jdbcTemplate.query(
                sql,
                PRODUCT_ROW_MAPPER,
                sellerId
        );
    }
}