/*
 * InMemoryOrderItemRepository.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.orderitem.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.repository.OrderItemRepository;

/**
 * This repository manages OrderItem objects using an in-memory List.
 * The data is stored temporarily during application execution without
 * using any external database.
 */
@Repository("inMemoryOrderItemRepository")
public class InMemoryOrderItemRepository implements OrderItemRepository {

    // Stores order items in memory
    private final List<OrderItem> orderItems;

    // Initializes an empty order item collection
    public InMemoryOrderItemRepository() {

        this.orderItems = new ArrayList<>();
    }

    // Saves a new order item into the in-memory collection
    @Override
    public boolean save(final OrderItem orderItem) {

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

        orderItem.setOrderItemId(IdGenerator.getInstance().nextOrderItemId());

        return orderItems.add(orderItem);
    }

    // Updates an existing order item using order item id
    @Override
    public boolean update(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderItemId() <= 0) {
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

        for (int index = 0; index < orderItems.size(); index++) {

            OrderItem existingOrderItem = orderItems.get(index);

            if (existingOrderItem.getOrderItemId() == orderItem.getOrderItemId()) {

                orderItems.set(index, orderItem);

                return true;
            }
        }

        return false;
    }

    // Deletes an order item using order item id
    @Override
    public boolean delete(final int orderItemId) {

        if (orderItemId <= 0) {
            return false;
        }

        return orderItems.removeIf(item -> item.getOrderItemId() == orderItemId);
    }

    // Deletes all order items associated with an order id
    @Override
    public boolean deleteByOrderId(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        return orderItems.removeIf(item -> item.getOrderId() == orderId);
    }


    // Finds an order item using order item id
    @Override
    public OrderItem findById(final int orderItemId) {

        if (orderItemId <= 0) {
            return null;
        }

        for (OrderItem item : orderItems) {

            if (item.getOrderItemId() == orderItemId) {

                return item;
            }
        }

        return null;
    }

    // Retrieves all order items belonging to an order
    @Override
    public Collection<OrderItem> findByOrderId(final int orderId) {

        Collection<OrderItem> result = new ArrayList<>();

        if (orderId <= 0) {
            return result;
        }

        for (OrderItem item : orderItems) {

            if (item.getOrderId() == orderId) {

                result.add(item);
            }
        }

        return result;
    }

    // Retrieve all order items stored in memory
    @Override
    public Collection<OrderItem> findAll() {

        return new ArrayList<>(orderItems);
    }
}
