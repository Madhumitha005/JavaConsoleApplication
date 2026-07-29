/*
 * JdbcCartRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
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

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

/**
 * JDBC implementation of the CartRepository interface.
 *
 * This class performs CRUD operations for carts
 * using Spring JdbcTemplate.
 */
@Repository("jdbcCartRepository")
public class JdbcCartRepository
        implements CartRepository {

    // Logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCartRepository.class);

    // Spring JdbcTemplate used for database operation
    private final JdbcTemplate jdbcTemplate;

    /**
     * Creates a JdbcCartRepository.
     *
     * @param jdbcTemplate JdbcTemplate instance
     */
    public JdbcCartRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Maps a database row to a Cart object
    private final RowMapper<Cart> ROW_MAPPER = (resultSet, rowNum) -> {

                Cart cart = new Cart();

                cart.setCartId(resultSet.getInt("cart_id"));
                cart.setUserId(resultSet.getInt("user_id"));

                return cart;
            };

    /**
     * Saves a cart.
     *
     * @param cart cart to save
     * @return true if saved successfully
     */
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

    /**
     * Updates a cart.
     *
     * @param cart updated cart
     * @return true if updated successfully
     */
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

    /**
     * Deletes a cart by its ID.
     *
     * @param cartId cart ID
     * @return true if deleted successfully
     */
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

    /**
     * Finds a cart by its ID.
     *
     * @param cartId cart ID
     * @return matching cart or null
     */
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

    /**
     * Finds a cart by user ID.
     *
     * @param userId user ID
     * @return matching cart or null
     */
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

    /**
     * Returns all carts.
     *
     * @return collection of carts
     */

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

    /**
     * Returns all carts for the given user.
     *
     * @param userId user ID
     * @return collection of carts
     */
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