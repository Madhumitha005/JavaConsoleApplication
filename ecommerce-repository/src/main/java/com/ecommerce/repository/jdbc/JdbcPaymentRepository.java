/*
 * JdbcPaymentRepository.java
 *
 * Version 1.4
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.jdbc;

import com.ecommerce.common.enums.PaymentMethod;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.model.Payment;
import com.ecommerce.repository.PaymentRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * JDBC implementation of the PaymentRepository interface.
 *
 * Performs CRUD operations for payments
 * using Spring JdbcTemplate.
 */
@Repository("jdbcPaymentRepository")
public class JdbcPaymentRepository
        implements PaymentRepository {

    // JDBC template used for database operation
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcPaymentRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Maps a database row to a Payment object
    private static final RowMapper<Payment> PAYMENT_ROW_MAPPER = (resultSet, rowNum) -> {

                Payment payment = new Payment();

                payment.setPaymentId(resultSet.getInt("payment_id"));
                payment.setOrderId(resultSet.getInt("order_id"));
                payment.setPaymentMethod(PaymentMethod.fromId(resultSet.getInt("payment_method_id")));
                payment.setPaymentStatus(PaymentStatus.fromId(resultSet.getInt("payment_status_id")));
                payment.setAmount(resultSet.getDouble("amount"));
                payment.setTransactionId(resultSet.getString("transaction_id"));

                return payment;
            };

    /**
     * Saves a payment to the database.
     *
     * @param payment Payment to save.
     * @return true if the payment is saved successfully;
     *         otherwise false.
     */
    @Override
    public boolean save(final Payment payment) {

        if (payment == null) {
            return false;
        }

        if (payment.getOrderId() <= 0) {
            return false;
        }

        if (payment.getAmount() <= 0) {
            return false;
        }

        if (payment.getPaymentMethod() == null) {
            return false;
        }

        if (payment.getPaymentStatus() == null) {
            return false;
        }

        if (payment.getTransactionId() == null || payment.getTransactionId().isBlank()) {

            return false;
        }

        String sql = """
                INSERT INTO payment
                (
                    order_id,
                    payment_method_id,
                    payment_status_id,
                    amount,
                    transaction_id
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        int rowsAffected =
                jdbcTemplate.update(
                        sql,
                        payment.getOrderId(),
                        payment.getPaymentMethod().getId(),
                        payment.getPaymentStatus().getId(),
                        payment.getAmount(),
                        payment.getTransactionId()
                );

        return rowsAffected > 0;
    }

    /**
     * Finds a payment by its identifier.
     *
     * @param paymentId Payment identifier.
     * @return Matching payment, or null if not found.
     */
    @Override
    public Payment findById(
            final int paymentId) {

        if (paymentId <= 0) {
            return null;
        }

        String sql = """
                SELECT
                    payment_id,
                    order_id,
                    payment_method_id,
                    payment_status_id,
                    amount,
                    transaction_id
                FROM payment
                WHERE payment_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        PAYMENT_ROW_MAPPER,
                        paymentId
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds a payment using the order identifier.
     *
     * @param orderId Order identifier.
     * @return Matching payment, or null if not found.
     */
    @Override
    public Payment findByOrderId(final int orderId) {

        if (orderId <= 0) {
            return null;
        }

        String sql = """
                SELECT
                    payment_id,
                    order_id,
                    payment_method_id,
                    payment_status_id,
                    amount,
                    transaction_id
                FROM payment
                WHERE order_id = ?
                """;

        return jdbcTemplate.query(
                        sql,
                        PAYMENT_ROW_MAPPER,
                        orderId
                )
                .stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Retrieves all payments.
     *
     * @return Collection of all payments.
     */
    @Override
    public Collection<Payment> findAll() {

        String sql = """
                SELECT
                    payment_id,
                    order_id,
                    payment_method_id,
                    payment_status_id,
                    amount,
                    transaction_id
                FROM payment
                ORDER BY payment_id
                """;

        return jdbcTemplate.query(
                sql,
                PAYMENT_ROW_MAPPER
        );
    }

    /**
     * Updates an existing payment.
     *
     * @param payment Payment to update.
     * @return true if the payment is updated successfully;
     *         otherwise false.
     */
    @Override
    public boolean update(final Payment payment) {

        if (payment == null) {
            return false;
        }

        if (payment.getPaymentId() <= 0) {
            return false;
        }

        if (payment.getAmount() <= 0) {
            return false;
        }

        if (payment.getPaymentMethod() == null) {
            return false;
        }

        if (payment.getPaymentStatus() == null) {
            return false;
        }

        if (payment.getTransactionId() == null || payment.getTransactionId().isBlank()) {

            return false;
        }

        String sql = """
                UPDATE payment
                SET
                    payment_method_id = ?,
                    payment_status_id = ?,
                    amount = ?,
                    transaction_id = ?
                WHERE payment_id = ?
                """;
 int rowsAffected =
                jdbcTemplate.update(
                        sql,
                        payment.getPaymentMethod().getId(),
                        payment.getPaymentStatus().getId(),
                        payment.getAmount(),
                        payment.getTransactionId(),
                        payment.getPaymentId()
                );

        return rowsAffected > 0;
    }

    /**
     * Deletes a payment from the database.
     *
     * @param paymentId Payment identifier.
     * @return true if the payment is deleted successfully;
     *         otherwise false.
     */
    @Override
    public boolean delete(final int paymentId) {

        if (paymentId <= 0) {
            return false;
        }

        String sql = """
                DELETE FROM payment
                WHERE payment_id = ?
                """;

        return jdbcTemplate.update(
                sql,
                paymentId
        ) > 0;
    }
}