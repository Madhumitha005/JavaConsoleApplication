/*
 * OrderRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import java.util.Collection;

import com.ecommerce.model.Order;

/**
 * Repository interface for managing Order entities.
 *
 * Defines CRUD operations and user-based order retrieval operations
 * for order management.
 *
 * Implementations may store order data using different storage
 * mechanisms such as database or in-memory collections.
 */
public interface OrderRepository {


    boolean save(final Order order);

    boolean update(final Order order);

    boolean delete(final int orderId);

    Order findById(final int orderId);

    Collection<Order> findByUserId(final int userId);

    Collection<Order> findAll();
}
