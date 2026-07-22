package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import java.util.Collection;

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Place Order
    public boolean placeOrder(Order order) {
        return orderService.placeOrder(order);
    }

    // User Orders
    public Collection<Order> viewOrders(int userId) {
        return orderService.viewOrders(userId);
    }

    // Admin View All Orders
    public Collection<Order> viewAllOrders() {
        return orderService.viewOrders();
    }
}