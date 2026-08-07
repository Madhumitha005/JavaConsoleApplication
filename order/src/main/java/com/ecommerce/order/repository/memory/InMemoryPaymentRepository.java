/*
 * InMemoryPaymentRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.

package com.ecommerce.order.repository.memory;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.order.entity.Payment;
import com.ecommerce.order.repository.PaymentRepository;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

/**
 * In-memory implementation of PaymentRepository.
 *
 * This repository manages Payment objects using an in-memory collection.
 * The data is temporarily stored during application execution without
 * using an external database.
 *
 * Provides payment storage, retrieval, update, and delete operations.
@Repository("inMemoryPaymentRepository")
public class InMemoryPaymentRepository
        implements PaymentRepository {

    // Stores payment objects in memory
    private final Collection<Payment> payments;

    // Initializes an empty payment collection
    public InMemoryPaymentRepository() {

        this.payments = new ArrayList<>();
    }

    /**
     * Saves a new payment into the in-memory collection.
     *
     * @param payment payment object to be saved
     * @return true if payment is saved successfully, otherwise false

    @Override
    public boolean save(final Payment payment) {

        if (payment == null) {
            return false;
        }
        payment.setPaymentId(IdGenerator.getInstance().nextPaymentId());

        return payments.add(payment);
    }

    /**
     * Finds a payment using payment id.
     *
     * @param paymentId unique identifier of payment
     * @return matching Payment object, otherwise null

    @Override
    public Payment findById(final int paymentId) {

        if (paymentId <= 0) {
            return null;
        }

        for (Payment payment : payments) {

            if (payment.getPaymentId() == paymentId) {

                return payment;
            }
        }
        return null;
    }

    /**
     * Finds a payment using order id.
     *
     * @param orderId unique identifier of order
     * @return matching Payment object, otherwise null

    @Override
    public Payment findByOrderId(final int orderId) {

        if (orderId <= 0) {
            return null;
        }

        for (Payment payment : payments) {

            if (payment.getOrderId() == orderId) {

                return payment;
            }
        }

        return null;
    }

    /**
     * Retrieves all payments stored in memory.
     *
     * @return collection containing all payments

    @Override
    public Collection<Payment> findAll() {

        return new ArrayList<>(payments);
    }

    /**
     * Updates an existing payment using payment id.
     *
     * @param updatedPayment updated payment object
     * @return true if payment is updated successfully, otherwise false

    @Override
    public boolean update(final Payment updatedPayment) {

        if (updatedPayment == null) {
            return false;
        }

        for (Payment payment : payments) {

            if (payment.getPaymentId() == updatedPayment.getPaymentId()) {

                payment.setOrderId(updatedPayment.getOrderId());
                payment.setPaymentMethod(updatedPayment.getPaymentMethod());
                payment.setPaymentStatus(updatedPayment.getPaymentStatus());
                payment.setAmount(updatedPayment.getAmount());
                payment.setTransactionId(updatedPayment.getTransactionId());

                return true;
            }
        }

        return false;
    }

    /**
     * Deletes a payment using payment id.
     *
     * @param paymentId unique identifier of payment
     * @return true if payment is deleted successfully, otherwise false

    @Override
    public boolean delete(final int paymentId) {

        return payments.removeIf(payment -> payment.getPaymentId() == paymentId);
    }
}
 */
