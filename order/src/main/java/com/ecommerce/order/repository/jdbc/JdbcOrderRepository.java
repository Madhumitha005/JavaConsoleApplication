/*
 * JdbcOrderRepository.java
 *
 * Version 2.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.order.repository.jdbc;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.PaymentMethod;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.repository.OrderRepository;

@Repository("jdbcOrderRepository")
public class JdbcOrderRepository implements OrderRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = Objects.requireNonNull(jdbcTemplate, "JdbcTemplate cannot be null.");
    }

    private static final RowMapper<Order> ORDER_ROW_MAPPER = (resultSet, rowNum) -> {

                Order order = new Order();

                order.setOrderId(resultSet.getInt("order_id"));
                order.setUserId(resultSet.getInt("user_id"));
                order.setSellerId(resultSet.getInt("seller_id"));
                order.setCustomerName(resultSet.getString("customer_name"));
                order.setPhone(resultSet.getString("phone"));
                order.setAddress(resultSet.getString("address"));
                order.setTotalAmount(resultSet.getDouble("total_amount"));
                order.setTransactionId(resultSet.getString("transaction_id"));
                order.setOrderStatus(OrderStatus.fromId(resultSet.getInt("order_status")));
                order.setPaymentMethod(PaymentMethod.fromId(resultSet.getInt("payment_method")));
                order.setPaymentStatus(PaymentStatus.fromId(resultSet.getInt("payment_status")));
                return order;
            };

    @Override
    public boolean save(final Order order) {

        String sql = """
                INSERT INTO "order"
                (
                    user_id,
                    seller_id,
                    customer_name,
                    phone,
                    address,
                    total_amount,
                    payment_method,
                    payment_status,
                    transaction_id,
                    order_status
                )
                VALUES
                (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                RETURNING order_id
                """;

        Integer orderId = jdbcTemplate.queryForObject(
                        sql,
                        Integer.class,
                        order.getUserId(),
                        order.getSellerId(),
                        order.getCustomerName(),
                        order.getPhone(),
                        order.getAddress(),
                        order.getTotalAmount(),
                        order.getPaymentMethod().getId(),
                        order.getPaymentStatus().getId(),
                        order.getTransactionId(),
                        order.getOrderStatus().getId()
                );

        if (orderId == null) {
            return false;
        }

        order.setOrderId(orderId);

        return true;
    }

    @Override
    public boolean update(final Order order) {

        String sql = """
                UPDATE "order"
                SET
                    customer_name = ?,
                    seller_id = ?,
                    phone = ?,
                    address = ?,
                    total_amount = ?,
                    payment_method = ?,
                    payment_status = ?,
                    transaction_id = ?,
                    order_status = ?
                WHERE order_id = ?
                """;

        int rowsAffected = jdbcTemplate.update(
                        sql,
                        order.getSellerId(),
                        order.getCustomerName(),
                        order.getPhone(),
                        order.getAddress(),
                        order.getTotalAmount(),
                        order.getPaymentMethod().getId(),
                        order.getPaymentStatus().getId(),
                        order.getTransactionId(),
                        order.getOrderStatus().getId(),
                        order.getOrderId()
                );

        return rowsAffected > 0;
    }

    @Override
    public boolean delete(final int orderId) {

        String sql =
                """
                DELETE FROM "order"
                WHERE order_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                orderId
        ) > 0;
    }

    @Override
    public Order findById(final int orderId) {

        String sql =
                """
                SELECT *
                FROM "order"
                WHERE order_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        ORDER_ROW_MAPPER,
                        orderId
                ).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Order> findByUserId(final int userId) {

        String sql =
                """
                SELECT *
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

    @Override
    public Collection<Order> findBySellerId(
            final int sellerId) {

        String sql = """
            SELECT *
            FROM "order"
            WHERE seller_id = ?
            ORDER BY order_id
            """;

        return jdbcTemplate.query(
                sql,
                ORDER_ROW_MAPPER,
                sellerId
        );
    }

    @Override
    public Collection<Order> findAll() {

        String sql =
                """
                SELECT *
                FROM "order"
                ORDER BY order_id
                """;

        return jdbcTemplate.query(
                sql,
                ORDER_ROW_MAPPER
        );
    }
}