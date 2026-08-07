/*
 * JdbcCartItemRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.cartitem.repository.jdbc;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartitem.entity.CartItem;
import com.ecommerce.cartitem.repository.CartItemRepository;

@Repository("jdbcCartItemRepository")
public class JdbcCartItemRepository implements CartItemRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcCartItemRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    private static final RowMapper<CartItem> CART_ITEM_ROW_MAPPER = (resultSet, rowNum) -> {

                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(resultSet.getInt("id"));
                cartItem.setUserId(resultSet.getInt("user_id"));
                cartItem.setProductId(resultSet.getInt("product_id"));
                cartItem.setQuantity(resultSet.getInt("quantity"));

                return cartItem;
            };

    @Override
    public boolean save(final CartItem cartItem) {

        final String sql = """
                INSERT INTO cart_item
                (
                    user_id,
                    product_id,
                    quantity
                )
                VALUES (?, ?, ?)
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                cartItem.getUserId(),
                cartItem.getProductId(),
                cartItem.getQuantity()
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean update(final CartItem cartItem) {

        final String sql = """
                UPDATE cart_item
                SET quantity = ?
                WHERE id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                cartItem.getQuantity(),
                cartItem.getCartItemId()
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(final int cartItemId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                cartItemId
        );

        return rowsAffected > 0;
    }

    @Override
    public boolean deleteByUserId(final int userId) {

        final String sql = """
                DELETE FROM cart_item
                WHERE user_id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                sql,
                userId
        );

        return rowsAffected > 0;
    }

    @Override
    public CartItem findById(final int cartItemId) {

        final String sql = """
                SELECT *
                FROM cart_item
                WHERE id = ?
                """;

        List<CartItem> cartItems = jdbcTemplate.query(
                sql,
                CART_ITEM_ROW_MAPPER,
                cartItemId
        );

        return cartItems.isEmpty()
                ? null
                : cartItems.getFirst();
    }

    @Override
    public Collection<CartItem> findByUserId(final int userId) {

        final String sql = """
                SELECT *
                FROM cart_item
                WHERE user_id = ?
                """;

        return jdbcTemplate.query(
                sql,
                CART_ITEM_ROW_MAPPER,
                userId
        );
    }

    @Override
    public Collection<CartItem> findAll() {

        final String sql = """
                SELECT *
                FROM cart_item
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                CART_ITEM_ROW_MAPPER
        );
    }
}