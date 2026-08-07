/*
 * OrderRepository.java
 *
 * Version 2.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.order.repository;

import java.util.Collection;

import com.ecommerce.order.entity.Order;

// Repository interface for order management
public interface OrderRepository {

    // Saves an order
    boolean save(Order order);

    // Updates an order
    boolean update(Order order);

    // Deletes an order
    boolean delete(int orderId);

    // Finds an order by Id
    Order findById(int orderId);

    // Finds orders by user Id
    Collection<Order> findByUserId(int userId);

    // Finds orders by seller Id
    Collection<Order> findBySellerId(int sellerId);

    // Returns all orders
    Collection<Order> findAll();

}