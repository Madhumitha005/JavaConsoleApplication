package com.ecommerce.service;

import java.util.Collection;
import java.util.Collections;

import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;

public class OrderItemService {

    private final OrderItemRepository memoryRepository;
    private final OrderItemRepository jdbcRepository;

    // Constructor Injection
    public OrderItemService(OrderItemRepository memoryRepository,
                            OrderItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    // Save Order Item
    public boolean addOrderItem(OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderId() <= 0 ||
            orderItem.getProductId() <= 0 ||
            orderItem.getQuantity() <= 0 ||
            orderItem.getPrice() <= 0) {

            return false;
        }

        boolean memorySaved = memoryRepository.save(orderItem);
        boolean jdbcSaved = jdbcRepository.save(orderItem);

        return memorySaved && jdbcSaved;
    }

    // Find By ID
    public OrderItem getOrderItemById(int orderItemId) {

        OrderItem orderItem = memoryRepository.findById(orderItemId);

        if (orderItem == null) {
            orderItem = jdbcRepository.findById(orderItemId);
        }

        return orderItem;
    }

    // Find By Order ID
    public Collection<OrderItem> getOrderItemsByOrderId(int orderId) {

        Collection<OrderItem> items =
                memoryRepository.findByOrderId(orderId);

        if (items == null || items.isEmpty()) {
            items = jdbcRepository.findByOrderId(orderId);
        }

        return items == null ? Collections.emptyList() : items;
    }

    // View All
    public Collection<OrderItem> getAllOrderItems() {

        Collection<OrderItem> items =
                memoryRepository.findAll();

        if (items == null || items.isEmpty()) {
            items = jdbcRepository.findAll();
        }

        return items == null ? Collections.emptyList() : items;
    }

    // Update
    public boolean updateOrderItem(OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        boolean memoryUpdated = memoryRepository.update(orderItem);
        boolean jdbcUpdated = jdbcRepository.update(orderItem);

        return memoryUpdated && jdbcUpdated;
    }

    // Delete By Item ID
    public boolean deleteOrderItem(int orderItemId) {

        boolean memoryDeleted = memoryRepository.delete(orderItemId);
        boolean jdbcDeleted = jdbcRepository.delete(orderItemId);

        return memoryDeleted && jdbcDeleted;
    }

    // Delete All Items Of Order
    public boolean deleteOrderItemsByOrderId(int orderId) {

        boolean memoryDeleted = memoryRepository.deleteByOrderId(orderId);
        boolean jdbcDeleted = jdbcRepository.deleteByOrderId(orderId);

        return memoryDeleted && jdbcDeleted;
    }
}