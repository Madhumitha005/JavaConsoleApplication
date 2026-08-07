/*
 * OrderItemService.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.orderitem.service;

import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service class responsible for order item management operations.
 * Handles adding, retrieving, updating, and deleting order items.
 * This service maintains order item data consistency between
 * JDBC and in-memory repository implementations.
 */
@Service
public class OrderItemService {

    private final OrderItemRepository memoryRepository;
    private final OrderItemRepository jdbcRepository;

    // Creates OrderItemService with required repository dependencies
    public OrderItemService(

            @Qualifier("inMemoryOrderItemRepository")
            final OrderItemRepository memoryRepository,
            @Qualifier("jdbcOrderItemRepository")
            final OrderItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

     // Adds a new order item
    public boolean addOrderItem(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderId() <= 0) {
            return false;
        }

        if (orderItem.getProductId() <= 0) {
            return false;
        }

        if (orderItem.getQuantity() <= 0) {
            return false;
        }

        boolean jdbcSaved = jdbcRepository.save(orderItem);

        if (!jdbcSaved) {
            return false;
        }

        return memoryRepository.save(orderItem);
    }

    // Adds multiple order items
    public boolean addOrderItems(final Collection<OrderItem> orderItems) {

        if (orderItems == null || orderItems.isEmpty()) {

            return false;
        }

        for (final OrderItem orderItem : orderItems) {

            boolean saved = addOrderItem(orderItem);

            if (!saved) {
                return false;
            }
        }

        return true;
    }

    // Retrieves an order item using order item id
    public OrderItem getOrderItemById(final int orderItemId) {

        if (orderItemId <= 0) {
            return null;
        }

        OrderItem orderItem = jdbcRepository.findById(orderItemId);

        if (orderItem == null) {

            orderItem = memoryRepository.findById(orderItemId);
        }
        return orderItem;
    }

    // Retrieves all order items belonging to an order
    public Collection<OrderItem> getOrderItemsByOrderId(final int orderId) {

        if (orderId <= 0) {

            return java.util.Collections.emptyList();
        }

        return jdbcRepository.findByOrderId(orderId);
    }

    public Collection<OrderItem> getAllOrderItems() {

        return jdbcRepository.findAll();
    }

    // Updates an existing order item
    public boolean updateOrderItem(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderItemId() <= 0) {
            return false;
        }

        boolean jdbcUpdated = jdbcRepository.update(orderItem);

        if (!jdbcUpdated) {
            return false;
        }

        memoryRepository.update(orderItem);

        return true;
    }

    // Retrieves all order items
    public boolean deleteOrderItem(final int orderItemId) {

        if (orderItemId <= 0) {
            return false;
        }

        boolean jdbcDeleted = jdbcRepository.delete(orderItemId);

        if (!jdbcDeleted) {
            return false;
        }

        memoryRepository.delete(orderItemId);

        return true;
    }

    // Deletes an order item using order item id
    public boolean deleteOrderItemsByOrderId(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        boolean jdbcDeleted = jdbcRepository.deleteByOrderId(orderId);
        memoryRepository.deleteByOrderId(orderId);

        return jdbcDeleted ;
    }
}