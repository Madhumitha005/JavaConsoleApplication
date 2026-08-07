/*
 * OrderItemRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.orderitem.repository;

import java.util.Collection;

import com.ecommerce.orderitem.entity.OrderItem;

public interface OrderItemRepository {

    // Save the Orderitem
    boolean save(OrderItem orderItem);

    // Update the Orderitem
    boolean update(OrderItem orderItem);

    // Delete the Order item
    boolean delete(int orderItemId);

    // Delete the order by id
    boolean deleteByOrderId(int orderId);

    // Find the orderitem by id
    OrderItem findById(int orderItemId);

    // Find the order id
    Collection<OrderItem> findByOrderId(int orderId);

    // Find all order item
    Collection<OrderItem> findAll();
}