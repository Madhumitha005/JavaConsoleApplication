/*
 * OrderItemController.java
 *
 * Version 1.5
 *
 * July 26, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.stereotype.Controller;

import com.ecommerce.model.OrderItem;
import com.ecommerce.service.OrderItemService;

@Controller
public class OrderItemController {

    private final OrderItemService orderItemService;

    // Constructor Injection
    public OrderItemController(final OrderItemService orderItemService) {

        this.orderItemService = Objects.requireNonNull(orderItemService, "OrderItemService cannot be null");
    }

    // Add Order Item
    public boolean addOrderItem(final OrderItem orderItem) {

        Objects.requireNonNull(orderItem, "OrderItem cannot be null");

        return orderItemService.addOrderItem(orderItem);
    }

    // Get Order Item By Id
    public OrderItem getOrderItemById(final int orderItemId) {

        return orderItemService.getOrderItemById(orderItemId);
    }

    // Get Items By Order Id
    public Collection<OrderItem> getOrderItemsByOrderId(final int orderId) {

        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    // View All Order Items
    public Collection<OrderItem> viewOrderItems() {

        return orderItemService.getAllOrderItems();
    }

    // Update Order Item
    public boolean updateOrderItem(final OrderItem orderItem) {

        Objects.requireNonNull(orderItem, "OrderItem cannot be null");

        return orderItemService.updateOrderItem(orderItem);
    }

    // Delete Order Item
    public boolean deleteOrderItem(final int orderItemId) {

        return orderItemService.deleteOrderItem(orderItemId);
    }

    // Delete All Items Of Order
    public boolean deleteOrderItemsByOrderId(final int orderId) {

        return orderItemService.deleteOrderItemsByOrderId(orderId);
    }
}