/*
 * OrderItemRepository.java
 *
 * Version 1.2
 *
 * July 27, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository;

import java.util.Collection;

import com.ecommerce.model.OrderItem;

/**
 * Repository interface for managing OrderItem entities.
 *
 * Defines CRUD operations and order-based item retrieval operations
 * for order item management.
 *
 * Implementations may store order item data using different storage
 * mechanisms such as database or in-memory collections.
 */
public interface OrderItemRepository {

    boolean save(final OrderItem orderItem);

    boolean update(final OrderItem orderItem);

    boolean delete(final int orderItemId);

    boolean deleteByOrderId(final int orderId);

    OrderItem findById(final int orderItemId);

    Collection<OrderItem> findByOrderId(final int orderId);

    Collection<OrderItem> findAll();
}
