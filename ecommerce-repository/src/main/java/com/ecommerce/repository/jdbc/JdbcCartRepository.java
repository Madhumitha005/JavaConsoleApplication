package com.ecommerce.repository.jdbc;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

@Repository("jdbcCartRepository")
public class JdbcCartRepository
        implements CartRepository {

    // Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCartRepository.class);

    // JDBC Template
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcCartRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Row Mapper
    private final RowMapper<Cart> ROW_MAPPER = (resultSet, rowNum) -> {

                Cart cart = new Cart();

                cart.setCartId(resultSet.getInt("cart_id"));
                cart.setUserId(resultSet.getInt("user_id"));

                return cart;
            };

    // Save Cart
    @Override
    public boolean save(final Cart cart) {

        final String sql = """
                INSERT INTO cart
                (user_id)
                VALUES (?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                cart.getUserId()
        );

        if (rows > 0) {

            LOGGER.info("Cart saved successfully.");

            return true;
        }

        LOGGER.warn("Cart save failed.");

        return false;
    }

    // Update Cart
    @Override
    public boolean update(final Cart cart) {

        final String sql = """
                UPDATE cart
                SET user_id = ?
                WHERE cart_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                cart.getUserId(),
                cart.getCartId()
        );

        if (rows > 0) {

            LOGGER.info("Cart updated successfully. " + "Cart ID: {}", cart.getCartId());

            return true;
        }

        LOGGER.warn("Cart update failed. " + "Cart ID: {}", cart.getCartId());

        return false;
    }

    // Delete Cart
    @Override
    public boolean delete(final int cartId) {

        final String sql = """
                DELETE FROM cart
                WHERE cart_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                cartId
        );

        if (rows > 0) {

            LOGGER.info("Cart deleted successfully. " + "Cart ID: {}", cartId);

            return true;
        }

        LOGGER.warn("Cart delete failed. " + "Cart ID not found: {}", cartId
        );

        return false;
    }

    // Find Cart By ID
    @Override
    public Cart findById(final int cartId) {

        final String sql = """
                SELECT *
                FROM cart
                WHERE cart_id = ?
                """;

        List<Cart> carts = jdbcTemplate.query(
                        sql,
                        ROW_MAPPER,
                        cartId
                );

        return carts.isEmpty() ? null : carts.getFirst();
    }

    // Find User Cart By User ID
    @Override
    public Cart findByUserId(final int userId) {

        final String sql = """
                SELECT *
                FROM cart
                WHERE user_id = ?
                LIMIT 1
                """;

        List<Cart> carts =
                jdbcTemplate.query(
                        sql,
                        ROW_MAPPER,
                        userId
                );

        return carts.isEmpty() ? null : carts.getFirst();
    }

    // Find All Carts
    @Override
    public Collection<Cart> findAll() {

        final String sql = """
                SELECT *
                FROM cart
                """;

        return jdbcTemplate.query(
                sql,
                ROW_MAPPER
        );
    }

    // Find All Carts By User ID
    @Override
    public Collection<Cart> findByUserIdList(final int userId) {

        final String sql = """
                SELECT *
                FROM cart
                WHERE user_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                ROW_MAPPER,
                userId
        );
    }
}