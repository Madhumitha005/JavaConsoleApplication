package com.ecommerce.service;

import java.util.Collection;
import java.util.Collections;

import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;

public class OrderService {

    private final OrderRepository memoryRepository;
    private final OrderRepository jdbcRepository;

    // Constructor Injection
    public OrderService(OrderRepository memoryRepository,
                        OrderRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    // Place Order
    public boolean placeOrder(Order order) {

        if (order == null) {
            return false;
        }

        if (order.getUserId() <= 0) {
            return false;
        }

        if (order.getCustomerName() == null ||
                order.getCustomerName().isBlank()) {
            return false;
        }

        if (order.getPhone() == null ||
                order.getPhone().isBlank()) {
            return false;
        }

        if (order.getAddress() == null ||
                order.getAddress().isBlank()) {
            return false;
        }

        if (order.getTotalAmount() <= 0) {
            return false;
        }

        boolean memorySaved = memoryRepository.placeOrder(order);
        boolean jdbcSaved = jdbcRepository.placeOrder(order);

        return memorySaved && jdbcSaved;
    }

    // View Orders By User
    public Collection<Order> viewOrders(int userId) {

        if (userId <= 0) {
            return Collections.emptyList();
        }

        Collection<Order> orders =
                memoryRepository.getOrdersByUser(userId);

        if (orders == null || orders.isEmpty()) {

            orders = jdbcRepository.getOrdersByUser(userId);
        }

        if (orders == null) {
            return Collections.emptyList();
        }

        return orders;
    }

    // View All Orders
    public Collection<Order> viewOrders() {

        Collection<Order> orders =
                memoryRepository.getOrders();

        if (orders == null || orders.isEmpty()) {

            orders = jdbcRepository.getOrders();
        }

        if (orders == null) {
            return Collections.emptyList();
        }

        return orders;
    }
}