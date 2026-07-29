/*
 * InMemoryOrderRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;

/**
 * In-memory implementation of OrderRepository.
 *
 * This repository manages Order objects using an in-memory collection.
 * The data is stored temporarily during application execution without
 * connecting to an external database.
 *
 * Provides CRUD operations and user-based order retrieval operations.
 */
@Repository("inMemoryOrderRepository")
public class InMemoryOrderRepository
        implements OrderRepository {

    // Stores order objects in memory
    private final Collection<Order> orders;

    // Initializes an empty order collection
    public InMemoryOrderRepository() {

        this.orders = new ArrayList<>();
    }

    /**
     * Saves a new order into the in-memory collection.
     *
     * @param order order object to be saved
     * @return true if order is saved successfully, otherwise false
     */
    @Override
    public boolean save(final Order order) {

        if (order == null) {
            return false;
        }

        if (order.getUserId() <= 0) {
            return false;
        }

        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {

            return false;
        }

        if (order.getPhone() == null || order.getPhone().isBlank()) {

            return false;
        }

        if (order.getAddress() == null || order.getAddress().isBlank()) {

            return false;
        }

        if (order.getTotalAmount() <= 0) {
            return false;
        }

        order.setOrderId(IdGenerator.getInstance().nextOrderId());

        return orders.add(order);
    }

    /**
     * Updates an existing order using order id.
     *
     * @param order updated order object
     * @return true if order is updated successfully, otherwise false
     */
    @Override
    public boolean update(final Order order) {

        if (order == null) {
            return false;
        }

        if (order.getOrderId() <= 0) {
            return false;
        }

        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {

            return false;
        }

        if (order.getPhone() == null || order.getPhone().isBlank()) {

            return false;
        }

        if (order.getAddress() == null || order.getAddress().isBlank()) {

            return false;
        }

        if (order.getTotalAmount() <= 0) {
            return false;
        }

        for (Order existingOrder : orders) {

            if (existingOrder.getOrderId() == order.getOrderId()) {

                existingOrder.setUserId(order.getUserId());
                existingOrder.setCustomerName(order.getCustomerName());
                existingOrder.setPhone(order.getPhone());
                existingOrder.setAddress(order.getAddress());
                existingOrder.setTotalAmount(order.getTotalAmount());

                return true;
            }
        }
        return false;
    }

    /**
     * Deletes an order using order id.
     *
     * @param orderId unique identifier of order
     * @return true if order is deleted, otherwise false
     */
    @Override
    public boolean delete(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        return orders.removeIf(order -> order.getOrderId() == orderId);
    }

    /**
     * Finds an order using order id.
     *
     * @param orderId unique identifier of order
     * @return matching Order object, otherwise null
     */
    @Override
    public Order findById(final int orderId) {

        if (orderId <= 0) {
            return null;
        }

        for (Order order : orders) {

            if (order.getOrderId() == orderId) {

                return order;
            }
        }
        return null;
    }

    /**
     * Retrieves all orders belonging to a specific user.
     *
     * @param userId unique identifier of user
     * @return collection of orders associated with user
     */
    @Override
    public Collection<Order> findByUserId(final int userId) {

        Collection<Order> userOrders = new ArrayList<>();

        if (userId <= 0) {
            return userOrders;
        }

        for (Order order : orders) {

            if (order.getUserId() == userId) {

                userOrders.add(order);
            }
        }
        return userOrders;
    }

    /**
     * Retrieves all orders stored in memory.
     *
     * @return collection containing all orders
     */
    @Override
    public Collection<Order> findAll() {

        return new ArrayList<>(orders);
    }
}
