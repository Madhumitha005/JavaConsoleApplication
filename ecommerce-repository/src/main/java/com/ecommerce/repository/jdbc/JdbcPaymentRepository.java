package com.ecommerce.repository.jdbc;

import com.ecommerce.common.enums.PaymentMethod;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.model.Payment;
import com.ecommerce.repository.PaymentRepository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository("jdbcPaymentRepository")
public class JdbcPaymentRepository
        implements PaymentRepository {

    // JDBC Template
    private final JdbcTemplate jdbcTemplate;

    // Constructor Injection
    public JdbcPaymentRepository(final JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // Row Mapper
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

    // Save
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

    // Find By Id
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

    // Find Payment By Order Id
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

    // Find All
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

    // Update
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

    // Delete
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