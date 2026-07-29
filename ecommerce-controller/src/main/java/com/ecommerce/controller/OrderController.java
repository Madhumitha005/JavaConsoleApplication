/*
 * OrderController.java
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

import com.ecommerce.model.Order;
import com.ecommerce.model.OrderItem;
import com.ecommerce.service.OrderService;

@Controller
public class OrderController {

    private final OrderService orderService;

    // Constructor Injection
    public OrderController(final OrderService orderService) {

        this.orderService = Objects.requireNonNull(orderService, "OrderService cannot be null");
    }

    // Place Order
    public boolean placeOrder(final Order order, final Collection<OrderItem> orderItems) {

        Objects.requireNonNull(order, "Order cannot be null");
        Objects.requireNonNull(orderItems, "OrderItems cannot be null");

        return orderService.placeOrder(order, orderItems);
    }

    // View Order By User

    public Collection<Order> viewOrders(final int userId) {

        return orderService.viewOrders(userId);
    }

    // View All Orders
    public Collection<Order> viewAllOrders() {

        return orderService.viewOrders();
    }

    // Find Order By ID
    public Order findOrderById( final int orderId) {

        return orderService.findById( orderId );
    }

    // Update Order
    public boolean updateOrder( final Order order) {

        Objects.requireNonNull( order, "Order cannot be null" );

        return orderService.update( order );

    }
}