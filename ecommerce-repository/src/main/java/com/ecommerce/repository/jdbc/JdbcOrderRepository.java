/*
 * JdbcOrderRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.jdbc;

import java.util.Collection;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.model.Order;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.repository.OrderRepository;

/**
 * JDBC implementation of the OrderRepository interface.
 *
 * This repository performs CRUD operations
 * for orders using JdbcTemplate.
 */
@Repository("jdbcOrderRepository")
public class JdbcOrderRepository
        implements OrderRepository {

    // spring JDBC Template used for database operation
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcOrderRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    // Maps a database row to an Order object
    private static final RowMapper<Order> ORDER_ROW_MAPPER = (resultSet, rowNum) -> {

                Order order = new Order();

                order.setOrderId(resultSet.getInt("order_id"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setCustomerName(resultSet.getString("customer_name"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));
                order.setOrderStatus(OrderStatus.fromId(resultSet.getInt("order_status_id")));

                return order;
            };

    /**
     * Saves an order into the database.
     *
     * @param order Order to save.
     * @return true if saved successfully; otherwise false.
     */
    @Override
    public boolean save(final Order order) {

        if (order == null) {
            return false;
        }

        if (order.getUserId() <= 0) {
            return false;
        }

        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {

            return false;
        }

        if (order.getPhone() == null || order.getPhone().isBlank()) {

            return false;
        }

        if (order.getAddress() == null || order.getAddress().isBlank()) {

            return false;
        }

        if (order.getTotalAmount() <= 0) {
            return false;
        }

        final String sql = """
                INSERT INTO "order"
                (
                    user_id,
                    customer_name,
                    phone,
                    address,
                    total_amount,
                    order_status_id
                )
                VALUES (?, ?, ?, ?, ?, ?)
                RETURNING order_id
                """;

        final Integer generatedOrderId =
                jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        order.getUserId(),
                        order.getCustomerName(),
                        order.getPhone(),
                        order.getAddress(),
                        order.getTotalAmount(),
                        order.getOrderStatus().getId()
                );

        if (generatedOrderId == null || generatedOrderId <= 0) {

            return false;
        }

        order.setOrderId(generatedOrderId);

        return true;
    }

    /**
     * Updates an existing order.
     *
     * @param order Order to update.
     * @return true if updated successfully.
     */
    @Override
    public boolean update(final Order order) {

        if (order == null) {
            return false;
        }

        if (order.getOrderId() <= 0) {
            return false;
        }

        if (order.getOrderStatus() == null) {

            return false;
        }

        final String sql = """
                UPDATE "order"
                SET
                    customer_name = ?,
                    phone = ?,
                    address = ?,
                    total_amount = ?,
                    order_status_id = ?
                WHERE order_id = ?
                """;

        final int rowsAffected =
                jdbcTemplate.update(
                        sql,
                        order.getCustomerName(),
                        order.getPhone(),
                        order.getAddress(),
                        order.getTotalAmount(),
                        order.getOrderStatus().getId(),
                        order.getOrderId()
                );

        return rowsAffected > 0;
    }

    /**
     * Deletes an order from the database.
     *
     * @param orderId Order identifier.
     * @return true if the order is deleted;
     *         otherwise false.
     */
    @Override
    public boolean delete(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        final String sql = """
                DELETE FROM "order"
                WHERE order_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                orderId
        ) > 0;
    }

    /**
     * Finds an order by its identifier.
     *
     * @param orderId Order identifier.
     * @return Matching order, or null if not found.
     */
    @Override
    public Order findById(final int orderId) {

        if (orderId <= 0) {
            return null;
        }

        final String sql = """
                SELECT
                    order_id,
                    user_id,
                    customer_name,
                    phone,
                    address,
                    total_amount,
                    order_status_id
                FROM "order"
                WHERE order_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        ORDER_ROW_MAPPER,
                        orderId
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds all orders placed by a user.
     *
     * @param userId User identifier.
     * @return Collection of orders.
     */
    @Override
    public Collection<Order> findByUserId(final int userId) {

        if (userId <= 0) {
            return java.util.Collections.emptyList();
        }

        final String sql = """
                SELECT
                    order_id,
                    user_id,
                    customer_name,
                    phone,
                    address,
                    total_amount,
                    order_status_id
                FROM "order"
                WHERE user_id = ?
                ORDER BY order_id
                """;

        return jdbcTemplate.query(
                sql,
                ORDER_ROW_MAPPER,
                userId
        );
    }

    /**
     * Retrieves all orders.
     *
     * @return Collection of all orders.
     */
    @Override
    public Collection<Order> findAll() {

        final String sql = """
                SELECT
                    order_id,
                    user_id,
                    customer_name,
                    phone,
                    address,
                    total_amount,
                    order_status_id
                FROM "order"
                ORDER BY order_id
                """;

        return jdbcTemplate.query(
                sql,
                ORDER_ROW_MAPPER
        );
    }
}