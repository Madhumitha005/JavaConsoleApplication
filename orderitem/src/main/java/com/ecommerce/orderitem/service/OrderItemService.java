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

import java.util.Collection;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.repository.OrderItemRepository;

@Service
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(
            final OrderItemRepository orderItemRepository) {

        this.orderItemRepository = orderItemRepository;
    }

    // Add order item
    @Caching(evict = {
            @CacheEvict(value = "orderItems", allEntries = true),
            @CacheEvict(value = "orderItemsByOrder",
                    key = "#orderItem.orderId")
    })
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

        return orderItemRepository.save(orderItem);
    }

    // Add multiple order items
    @Caching(evict = {
            @CacheEvict(value = "orderItems", allEntries = true),
            @CacheEvict(value = "orderItemsByOrder", allEntries = true),
            @CacheEvict(value = "orderItemsById", allEntries = true)
    })
    public boolean addOrderItems(
            final Collection<OrderItem> orderItems) {

        if (orderItems == null || orderItems.isEmpty()) {
            return false;
        }

        for (final OrderItem orderItem : orderItems) {

            if (orderItem == null
                    || orderItem.getOrderId() <= 0
                    || orderItem.getProductId() <= 0
                    || orderItem.getQuantity() <= 0) {

                return false;
            }

            if (!orderItemRepository.save(orderItem)) {
                return false;
            }
        }

        return true;
    }

    // Find order item by ID
    @Cacheable(value = "orderItemsById", key = "#orderItemId")
    public OrderItem getOrderItemById(
            final int orderItemId) {

        if (orderItemId <= 0) {
            return null;
        }

        return orderItemRepository.findById(orderItemId);
    }

    // Find order items by order ID
    @Cacheable(value = "orderItemsByOrder", key = "#orderId")
    public Collection<OrderItem> getOrderItemsByOrderId(
            final int orderId) {

        if (orderId <= 0) {
            return java.util.Collections.emptyList();
        }

        return orderItemRepository.findByOrderId(orderId);
    }

    // Find all order items
    @Cacheable(value = "orderItems", key = "'all'")
    public Collection<OrderItem> getAllOrderItems() {

        return orderItemRepository.findAll();
    }

    // Update order item
    @Caching(evict = {
            @CacheEvict(value = "orderItems", allEntries = true),
            @CacheEvict(value = "orderItemsById",
                    key = "#orderItem.orderItemId"),
            @CacheEvict(value = "orderItemsByOrder",
                    allEntries = true)
    })
    public boolean updateOrderItem(
            final OrderItem orderItem) {

        if (orderItem == null
                || orderItem.getOrderItemId() <= 0) {

            return false;
        }

        return orderItemRepository.update(orderItem);
    }

    // Delete order item
    @Caching(evict = {
            @CacheEvict(value = "orderItems", allEntries = true),
            @CacheEvict(value = "orderItemsById",
                    key = "#orderItemId"),
            @CacheEvict(value = "orderItemsByOrder",
                    allEntries = true)
    })
    public boolean deleteOrderItem(
            final int orderItemId) {

        if (orderItemId <= 0) {
            return false;
        }

        return orderItemRepository.delete(orderItemId);
    }

    // Delete all items belonging to an order
    @Caching(evict = {
            @CacheEvict(value = "orderItems", allEntries = true),
            @CacheEvict(value = "orderItemsByOrder",
                    key = "#orderId"),
            @CacheEvict(value = "orderItemsById",
                    allEntries = true)
    })
    public boolean deleteOrderItemsByOrderId(
            final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        return orderItemRepository.deleteByOrderId(orderId);
    }
}