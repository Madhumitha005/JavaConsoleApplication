/*
 * JdbcCartItemRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.jdbc;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * JDBC implementation of the CartItemRepository interface.
 *
 * This class performs CRUD operations for cart items
 * using Spring JdbcTemplate.
 */
@Repository("jdbcCartItemRepository")
public class JdbcCartItemRepository implements CartItemRepository {

    // Logger for this class
    private final Logger LOGGER = LoggerFactory.getLogger(JdbcCartItemRepository.class);

    // spring JDBC Template used for DB operation
    private final JdbcTemplate jdbcTemplate;

    /**
     * Creates a JdbcCartItemRepository.
     *
     * @param jdbcTemplate JdbcTemplate instance
     */
    public JdbcCartItemRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Map a Database row to a CartItem Object
    private final RowMapper<CartItem> ROW_MAPPER = (resultSet, rowNum) -> {

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(resultSet.getInt("cart_item_id"));
        cartItem.setCartId(resultSet.getInt("cart_id"));
        cartItem.setProductId(resultSet.getInt("product_id"));
        cartItem.setQuantity(resultSet.getInt("quantity"));

        return cartItem;
    };

    /**
     * Saves a cart item.
            *
            * @param cartItem cart item to save
     * @return true if saved successfully
     */
    @Override
    public boolean save(final CartItem cartItem) {

        final String sql = """
                INSERT INTO cart_item
                (cart_id, product_id, quantity)
                VALUES (?, ?, ?)
                """;

        int rows = jdbcTemplate.update(
                sql,
                cartItem.getCartId(),
                cartItem.getProductId(),
                cartItem.getQuantity()
        );

        LOGGER.info("Cart Item Saved Successfully");

        return rows > 0;
    }

    /**
     * Updates the quantity of a cart item.
     *
     * @param cartItem updated cart item
     * @return true if updated successfully
     */
    @Override
    public boolean update(final CartItem cartItem) {

        final String sql = """
                UPDATE cart_item
                SET quantity = ?
                WHERE cart_item_id = ?
                """;

        int rows = jdbcTemplate.update(
                sql,
                cartItem.getQuantity(),
                cartItem.getCartItemId()
        );

        LOGGER.info("Cart Item Updated Successfully");

        return rows > 0;
    }

    /**
     * Deletes a cart item by its ID.
     *
     * @param cartItemId cart item ID
     * @return true if deleted successfully
     */
    @Override
    public boolean delete(final int cartItemId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE cart_item_id = ?
                """;

        int rows = jdbcTemplate.update(sql, cartItemId);

        LOGGER.info("Cart Item Deleted Successfully");

        return rows > 0;
    }

    /**
     * Deletes all cart items for a cart.
     *
     * @param cartId cart ID
     * @return true if deleted successfully
     */
    @Override
    public boolean deleteByCartId(final int cartId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE cart_id = ?
                """;

        int rows = jdbcTemplate.update(sql, cartId);

        LOGGER.info("Cart Items Deleted Successfully");

        return rows > 0;
    }

    /**
     * Finds a cart item by its ID.
     *
     * @param cartItemId cart item ID
     * @return matching CartItem or null
     */
    @Override
    public CartItem findById(final int cartItemId) {

        final String sql = """
                SELECT *
                FROM cart_item
                WHERE cart_item_id = ?
                """;

        List<CartItem> cartItems = jdbcTemplate.query(sql, ROW_MAPPER, cartItemId);

        return cartItems.isEmpty() ? null : cartItems.getFirst();
    }

    /**
     * Finds all items in a cart.
     *
     * @param cartId cart ID
     * @return collection of cart items
     */
    @Override
    public Collection<CartItem> findByCartId(final int cartId) {

        final String sql = """
                SELECT *
                FROM cart_item
                WHERE cart_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                ROW_MAPPER,
                cartId
        );
    }

    /**
     * Returns all cart items.
     *
     * @return collection of cart items
     */
    @Override
    public Collection<CartItem> findAll() {

        final String sql = """
                SELECT *
                FROM cart_item
                """;

        return jdbcTemplate.query(
                sql,
                ROW_MAPPER
        );
    }
}