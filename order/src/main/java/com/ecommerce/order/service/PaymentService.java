/*
 * PaymentService.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.

package com.ecommerce.order.service;

import com.ecommerce.order.entity.Payment;
import com.ecommerce.order.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Service class responsible for payment management operations.
 *
 * Handles adding payments, retrieving payment details,
 * and displaying payment records.
 *
 * This service maintains payment data consistency between
 * JDBC and in-memory repository implementations.

@Service
public class PaymentService {

    private final PaymentRepository memoryRepository;

    private final PaymentRepository jdbcRepository;

    /**
     * Creates PaymentService with required repository dependencies.
     *
     * @param memoryRepository in-memory payment repository
     * @param jdbcRepository JDBC payment repository

    public PaymentService(

            @Qualifier("inMemoryPaymentRepository")
            final PaymentRepository memoryRepository,

            @Qualifier("jdbcPaymentRepository")
            final PaymentRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    /**
     * Adds a new payment.
     *
     * Saves payment details into JDBC repository first
     * and then synchronizes the in-memory repository.
     *
     * @param payment payment object to be saved
     * @return true if payment is saved successfully,
     * otherwise false

    public boolean addPayment(final Payment payment) {

        if (payment == null) {
            return false;
        }

        boolean jdbcSaved = jdbcRepository.save(payment);

        if (!jdbcSaved) {
            return false;
        }

        boolean memorySaved = memoryRepository.save(payment);
        return memorySaved;
    }

    /**
     * Retrieves payment details using payment id.
     *
     * Searches JDBC repository first and falls back to
     * in-memory repository when payment is not found.
     *
     * @param paymentId unique identifier of payment
     * @return matching Payment object,
     * otherwise null

    public Payment getPaymentById(final int paymentId) {

        Payment payment = jdbcRepository.findById(paymentId);

        if (payment == null) {

            payment = memoryRepository.findById(paymentId);
        }
        return payment;
    }

    /**
     * Retrieves payment details using order id.
     *
     * Searches payment information associated with
     * a specific order.
     *
     * @param orderId unique identifier of order
     * @return matching Payment object,
     * otherwise null

    public Payment getPaymentByOrderId(final int orderId) {

        Payment payment = jdbcRepository.findByOrderId(orderId);

        if (payment == null) {

            payment = memoryRepository.findByOrderId(orderId);
        }
        return payment;
    }

    /**
     * Retrieves all payment records.
     *
     * Combines JDBC and memory repository results
     * while avoiding duplicate payment entries.
     *
     * @return collection containing all payments

    public Collection<Payment> viewPayments() {

        Map<Integer, Payment> payments = new LinkedHashMap<>();

        Collection<Payment> jdbcPayments = jdbcRepository.findAll();

        if (jdbcPayments != null) {

            for (Payment payment : jdbcPayments) {

                payments.put(payment.getPaymentId(), payment);
            }
        }

        Collection<Payment> memoryPayments = memoryRepository.findAll();

        if (memoryPayments != null) {

            for (Payment payment : memoryPayments) {

                payments.putIfAbsent(payment.getPaymentId(), payment);
            }
        }
        return payments.values();
    }
}
 */

