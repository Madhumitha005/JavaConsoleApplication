/*
 * JdbcOrderItemRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.orderitem.repository.jdbc;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.repository.OrderItemRepository;

// JDBC implementation of the OrderItemRepository interface
@Repository("jdbcOrderItemRepository")
public class JdbcOrderItemRepository implements OrderItemRepository {

    // Spring JdbcTemplate used for database operation
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcOrderItemRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    // Maps a database row to an OrderItem object
    private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = (resultSet, rowNum) -> {

                OrderItem orderItem = new OrderItem();

                orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
                orderItem.setOrderId(resultSet.getInt("order_id"));
                orderItem.setProductId(resultSet.getInt("product_id"));
                orderItem.setQuantity(resultSet.getInt("quantity"));

                return orderItem;
            };

    // Saves an order item
    @Override
    public boolean save(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderId() <= 0) {
            return false;
        }

        if (orderItem.getProductId() <= 0) {
            return false;
        }

        if (orderItem.getQuantity() <= 0) {
            return false;
        }

        final String sql = """
                INSERT INTO order_item
                (
                    order_id,
                    product_id,
                    quantity
                )
                VALUES (?, ?, ?)
                """;

        final int rowsAffected = jdbcTemplate.update(
                        sql,
                        orderItem.getOrderId(),
                        orderItem.getProductId(),
                        orderItem.getQuantity()
                        );

        return rowsAffected > 0;
    }

    // Finds an order item by its Id
    @Override
    public OrderItem findById(final int orderItemId) {

        if (orderItemId <= 0) {
            return null;
        }

        final String sql = """
                SELECT
                    order_item_id,
                    order_id,
                    product_id,
                    quantity
                FROM order_item
                WHERE order_item_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        ORDER_ITEM_ROW_MAPPER,
                        orderItemId
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    // Returns all order items for an order
    @Override
    public Collection<OrderItem> findByOrderId(final int orderId) {

        if (orderId <= 0) {
            return Collections.emptyList();
        }

        final String sql = """
                SELECT
                    order_item_id,
                    order_id,
                    product_id,
                    quantity
                FROM order_item
                WHERE order_id = ?
                ORDER BY order_item_id
                """;

        return jdbcTemplate.query(
                sql,
                ORDER_ITEM_ROW_MAPPER,
                orderId
        );
    }

    // Returns all order items
    @Override
    public Collection<OrderItem> findAll() {

        final String sql = """
                SELECT
                    order_item_id,
                    order_id,
                    product_id,
                    quantity
                FROM order_item
                ORDER BY order_item_id
                """;

        return jdbcTemplate.query(
                sql,
                ORDER_ITEM_ROW_MAPPER
        );
    }

    // Updates an order item
    @Override
    public boolean update(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderItemId() <= 0) {
            return false;
        }

        if (orderItem.getQuantity() <= 0) {
            return false;
        }

        final String sql = """
                UPDATE order_item
                SET
                    quantity = ?
                WHERE order_item_id = ?
                """;

        final int rowsAffected = jdbcTemplate.update(
                        sql,
                        orderItem.getQuantity(),
                        orderItem.getOrderItemId()
                );

        return rowsAffected > 0;
    }

    // Deletes an order item by its Id
    @Override
    public boolean delete(final int orderItemId) {

        if (orderItemId <= 0) {
            return false;
        }

        final String sql = """
                DELETE FROM order_item
                WHERE order_item_id = ?
                """;

        final int rowsAffected = jdbcTemplate.update(
                        sql,
                        orderItemId
                );

        return rowsAffected > 0;
    }

    // Deletes all order items for an order
    @Override
    public boolean deleteByOrderId(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        final String sql = """
                DELETE FROM order_item
                WHERE order_id = ?
                """;

        final int rowsAffected = jdbcTemplate.update(
                        sql,
                        orderId
                );

        return rowsAffected > 0;
    }
}