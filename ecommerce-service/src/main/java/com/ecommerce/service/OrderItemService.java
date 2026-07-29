/*
 * OrderItemService.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.service;

import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service class responsible for order item management operations.
 *
 * Handles adding, retrieving, updating, and deleting order items.
 *
 * This service maintains order item data consistency between
 * JDBC and in-memory repository implementations.
 */
@Service
public class OrderItemService {

    private final OrderItemRepository memoryRepository;
    private final OrderItemRepository jdbcRepository;

    /**
     * Creates OrderItemService with required repository dependencies.
     *
     * @param memoryRepository in-memory order item repository
     * @param jdbcRepository JDBC order item repository
     */
    public OrderItemService(

            @Qualifier("inMemoryOrderItemRepository")
            final OrderItemRepository memoryRepository,

            @Qualifier("jdbcOrderItemRepository")
            final OrderItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

     /**
     * Adds a new order item.
     *
     * Validates order item details and saves the item into
     * JDBC and memory repositories.
     *
     * @param orderItem order item object to be saved
     * @return true if order item is saved successfully, otherwise false
     */
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

        if (orderItem.getPrice() <= 0) {
            return false;
        }

        boolean jdbcSaved = jdbcRepository.save(orderItem);

        if (!jdbcSaved) {
            return false;
        }

        return memoryRepository.save(orderItem);
    }

    /**
     * Adds multiple order items.
     *
     * Saves each order item individually.
     *
     * @param orderItems collection of order items
     * @return true if all order items are saved successfully,
     * otherwise false
     */
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

    /**
     * Retrieves an order item using order item id.
     *
     * Searches JDBC repository first and memory repository
     * when item is not found.
     *
     * @param orderItemId unique identifier of order item
     * @return matching OrderItem object, otherwise null
     */
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

    /**
     * Retrieves all order items belonging to an order.
     *
     * @param orderId unique identifier of order
     * @return collection of order items
     */
    public Collection<OrderItem> getOrderItemsByOrderId(final int orderId) {

        if (orderId <= 0) {

            return java.util.Collections.emptyList();
        }

        return jdbcRepository.findByOrderId(orderId);
    }

    public Collection<OrderItem> getAllOrderItems() {

        return jdbcRepository.findAll();
    }

    /**
     * Updates an existing order item.
     *
     * Updates JDBC repository first and then synchronizes
     * the in-memory repository.
     *
     * @param orderItem updated order item object
     * @return true if order item is updated successfully,
     * otherwise false
     */
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

    /**
     * Retrieves all order items.
     *
     * @return collection containing all order items
     */
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

    /**
     * Deletes an order item using order item id.
     *
     * Deletes from JDBC repository first and then removes
     * the item from memory repository.
     *
     * @param orderItemId unique identifier of order item
     * @return true if order item is deleted successfully,
     * otherwise false
     */
    public boolean deleteOrderItemsByOrderId(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        boolean jdbcDeleted = jdbcRepository.deleteByOrderId(orderId);
        memoryRepository.deleteByOrderId(orderId);

        return jdbcDeleted ;
    }
}