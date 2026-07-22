package com.ecommerce.controller;

import java.util.Collection;
import java.util.Objects;

import com.ecommerce.model.OrderItem;
import com.ecommerce.service.OrderItemService;

public class OrderItemController {

    private final OrderItemService orderItemService;

    // Constructor Injection
    public OrderItemController(OrderItemService orderItemService) {

        this.orderItemService = Objects.requireNonNull(
                orderItemService,
                "OrderItemService cannot be null"
        );
    }

    // Add Order Item
    public boolean addOrderItem(OrderItem orderItem) {

        return orderItemService.addOrderItem(orderItem);
    }

    // Get Order Item By ID
    public OrderItem getOrderItemById(int orderItemId) {

        return orderItemService.getOrderItemById(orderItemId);
    }

    // Get Items By Order ID
    public Collection<OrderItem> getOrderItemsByOrderId(int orderId) {

        return orderItemService.getOrderItemsByOrderId(orderId);
    }

    // View All Order Items
    public Collection<OrderItem> viewOrderItems() {

        return orderItemService.getAllOrderItems();
    }

    // Update Order Item
    public boolean updateOrderItem(OrderItem orderItem) {

        return orderItemService.updateOrderItem(orderItem);
    }

    // Delete Order Item
    public boolean deleteOrderItem(int orderItemId) {

        return orderItemService.deleteOrderItem(orderItemId);
    }

    // Delete All Items Of Order
    public boolean deleteOrderItemsByOrderId(int orderId) {

        return orderItemService.deleteOrderItemsByOrderId(orderId);
    }
}