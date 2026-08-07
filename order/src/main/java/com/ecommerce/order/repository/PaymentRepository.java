/*
 * PaymentRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.

package com.ecommerce.order.repository;

import com.ecommerce.order.entity.Payment;
import java.util.Collection;

/**
 * Repository interface for managing Payment entities.
 *
 * Defines CRUD operations and payment retrieval operations
 * based on payment and order identifiers.
 *
 * Implementations may store payment data using different storage
 * mechanisms such as database or in-memory collections.

public interface PaymentRepository {

    boolean save(final Payment payment);

    boolean update(final Payment payment);

    boolean delete(final int paymentId);

    Payment findById(final int paymentId);

    Payment findByOrderId(final int orderId);

    Collection<Payment> findAll();
}
 */
