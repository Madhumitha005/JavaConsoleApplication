/*
 * InMemoryOrderRepository.java
 *
 * Version 2.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.order.repository.memory;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.repository.OrderRepository;

@Repository("inMemoryOrderRepository")
public class InMemoryOrderRepository implements OrderRepository {

    private final Collection<Order> orders;

    public InMemoryOrderRepository() {

        this.orders = new ArrayList<>();
    }

    @Override
    public boolean save(final Order order) {

        if (order == null) {

            return false;
        }

        if (order.getUserId() == null || order.getUserId() <= 0) {

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

        if (order.getTotalAmount() == null || order.getTotalAmount().doubleValue() <= 0) {

            return false;
        }

        if (order.getOrderId() == null) {

            order.setOrderId(
                    IdGenerator.getInstance()
                            .nextOrderId()
            );
        }
        return orders.add(order);
    }

    @Override
    public boolean update(final Order order) {

        if (order == null || order.getOrderId() == null) {

            return false;
        }

        for (Order existingOrder : orders) {

            if (existingOrder.getOrderId().equals(order.getOrderId())) {

                existingOrder.setSellerId(order.getSellerId());
                existingOrder.setCustomerName(order.getCustomerName());
                existingOrder.setPhone(order.getPhone());
                existingOrder.setAddress(order.getAddress());
                existingOrder.setTotalAmount(order.getTotalAmount());
                existingOrder.setOrderStatus(order.getOrderStatus());
                existingOrder.setPaymentMethod(order.getPaymentMethod());
                existingOrder.setPaymentStatus(order.getPaymentStatus());
                existingOrder.setTransactionId(order.getTransactionId());
                existingOrder.setUpdatedAt(order.getUpdatedAt());

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(final int orderId) {

        return orders.removeIf(
                order -> order.getOrderId().equals(orderId));
    }

    @Override
    public Order findById(final int orderId) {

        for (Order order : orders) {

            if (order.getOrderId().equals(orderId)) {

                return order;
            }
        }

        return null;
    }

    @Override
    public Collection<Order> findByUserId(final int userId) {

        Collection<Order> userOrders = new ArrayList<>();

        for (Order order : orders) {

            if (order.getUserId().equals(userId)) {

                userOrders.add(order);
            }
        }

        return userOrders;
    }

    @Override
    public Collection<Order> findBySellerId(
            final int sellerId) {

        Collection<Order> sellerOrders = new ArrayList<>();

        for (Order order : orders) {

            if (order.getSellerId().equals(sellerId)) {

                sellerOrders.add(order);
            }
        }

        return sellerOrders;
    }

    @Override
    public Collection<Order> findAll() {

        return new ArrayList<>(orders);
    }
}