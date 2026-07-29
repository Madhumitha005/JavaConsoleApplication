/*
 * OrderService.java
 *
 * Version 1.6
 *
 * July 28, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.model.Order;
import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.model.OrderItem;
import com.ecommerce.model.Product;
import com.ecommerce.repository.OrderItemRepository;
import com.ecommerce.repository.OrderRepository;
import com.ecommerce.repository.ProductRepository;

/** * Service class responsible for order management operations.
 *
 *  Handles order placement, order retrieval, order updates,
 *
 * customer return requests, and admin order status updates.
 *
 * This service maintains order data consistency between * JDBC and in-memory repository implementations.
 * */

@Service
public class OrderService {

    private final OrderRepository memoryOrderRepository;
    private final OrderRepository jdbcOrderRepository;
    private final OrderItemRepository memoryOrderItemRepository;
    private final OrderItemRepository jdbcOrderItemRepository;
    private final ProductRepository memoryProductRepository;
    private final ProductRepository jdbcProductRepository;

    public OrderService(

            @Qualifier("inMemoryOrderRepository")
            final OrderRepository memoryOrderRepository,

            @Qualifier("jdbcOrderRepository")
            final OrderRepository jdbcOrderRepository,

            @Qualifier("inMemoryOrderItemRepository")
            final OrderItemRepository memoryOrderItemRepository,

            @Qualifier("jdbcOrderItemRepository")
            final OrderItemRepository jdbcOrderItemRepository,

            @Qualifier("inMemoryProductRepository")
            final ProductRepository memoryProductRepository,

            @Qualifier("jdbcProductRepository")
            final ProductRepository jdbcProductRepository) {

        this.memoryOrderRepository = Objects.requireNonNull(memoryOrderRepository, "Memory Order Repository cannot be null.");
        this.jdbcOrderRepository = Objects.requireNonNull(jdbcOrderRepository, "JDBC Order Repository cannot be null.");
        this.memoryOrderItemRepository = Objects.requireNonNull(memoryOrderItemRepository, "Memory Order Item Repository cannot be null.");
        this.jdbcOrderItemRepository = Objects.requireNonNull(jdbcOrderItemRepository, "JDBC Order Item Repository cannot be null.");
        this.memoryProductRepository = Objects.requireNonNull(memoryProductRepository, "Memory Product Repository cannot be null.");
        this.jdbcProductRepository = Objects.requireNonNull(jdbcProductRepository, "JDBC Product Repository cannot be null.");
    }

    /**
     *  Places a new order with order items.
     *
     *  Validates product availability, calculates total order amount,
     *  saves order details and associated order items.
     *
     *  @param order order details
     *  @param orderItems collection of order items
     *  @return true if order is placed successfully,
     *
     *   otherwise false
     *   @throws IllegalStateException when order or order item
     *   saving fails */
    @Transactional
    public boolean placeOrder(final Order order, final Collection<OrderItem> orderItems) {

        if (order == null) {
            return false;
        }

        if (orderItems == null || orderItems.isEmpty()) {

            return false;
        }

        if (order.getUserId() <= 0) {
            return false;
        }

        double totalAmount = 0.0;


        for (final OrderItem item : orderItems) {

            if (item == null) {
                return false;
            }

            if (item.getProductId() <= 0) {
                return false;
            }

            if (item.getQuantity() <= 0) {
                return false;
            }

            Product product = jdbcProductRepository.findById(item.getProductId());

            if (product == null) {

                product = memoryProductRepository.findById(item.getProductId());
            }

            if (product == null) {

                throw new IllegalStateException("Product not found. Product ID: " + item.getProductId());
            }

            totalAmount += item.getPrice() * item.getQuantity();
        }

        order.setTotalAmount(totalAmount);
        order.setOrderStatus(OrderStatus.PENDING);

        boolean jdbcOrderSaved = jdbcOrderRepository.save(order);

        if (!jdbcOrderSaved) {

            throw new IllegalStateException("Order could not be saved.");
        }

        final int generatedOrderId = order.getOrderId();

        if (generatedOrderId <= 0) {

            throw new IllegalStateException("PostgreSQL Order ID was not generated.");
        }

        for (final OrderItem item : orderItems) {

            item.setOrderId(generatedOrderId);

            boolean jdbcItemSaved = jdbcOrderItemRepository.save(item);

            if (!jdbcItemSaved) {

                throw new IllegalStateException("Order item could not be saved.");
            }

            memoryOrderItemRepository.save(item);
        }
        return true;
    }

    /**
     * Retrieves all orders belonging to a specific user.
     *
     * Searches data from memory repository first and then
     * retrieves from JDBC repository if required.
     *
     * @param userId unique identifier of user
     * @return collection of user orders
     */
    public Collection<Order> viewOrders(final int userId) {

        if (userId <= 0) {

            return Collections.emptyList();
        }

        Collection<Order> orders =
                memoryOrderRepository.findByUserId(userId);

        if (orders == null || orders.isEmpty()) {
            orders = jdbcOrderRepository.findByUserId(userId);
        }

        return orders;
    }

    /**
     * Retrieves all orders available in the system.
     *
     * @return collection containing all orders
     * */
    public Collection<Order> viewOrders() {

        Collection<Order> orders = memoryOrderRepository.findAll();

        if (orders == null || orders.isEmpty()) {
            orders = jdbcOrderRepository.findAll();
        }

        return orders;
    }

    /**
     *  Finds an order using order id.
     *
     *  @param orderId unique identifier of order
     *  @return matching Order object,
     *  otherwise null
     */
    public Order findById(final int orderId) {

        if (orderId <= 0) {

            return null;
        }

        Order order = memoryOrderRepository.findById(orderId);

        if (order == null) {
            order = jdbcOrderRepository.findById(orderId);
        }

        return order;
    }

    /**
     * Updates an existing order.
     *
     * Updates order information in JDBC repository
     * and synchronizes changes with memory repository.
     *
     * @param order updated order details
     * @return true if order is updated successfully,
     *  otherwise false
     */
    public boolean update(final Order order) {

        if (order == null) {
            return false;
        }

        if (order.getOrderId() <= 0) {
            return false;
        }

        if (order.getOrderStatus() == null) {
            return false;
        }

        boolean jdbcUpdated = jdbcOrderRepository.update(order);

        if (jdbcUpdated) {
            memoryOrderRepository.update(order);
        }

        return jdbcUpdated;
    }

    /**
     * Handles customer return request.
     *
     * Only delivered orders are eligible for return requests.
     *
     * @param orderId unique identifier of order
     * @return true if return request is processed successfully,
     * otherwise false
     */
    public boolean requestReturn(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        Order order = jdbcOrderRepository.findById(orderId);

        if (order == null) {
            order = memoryOrderRepository.findById(orderId);
        }

        if (order == null) {
            return false;
        }

        // Only delivered orders can be returned
        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            return false;
        }

        order.setOrderStatus(OrderStatus.RETURN_REQUESTED);

        boolean jdbcUpdated = jdbcOrderRepository.update(order);

        if (jdbcUpdated) {
            memoryOrderRepository.update(order);
        }

        return jdbcUpdated;
    }

    /**
     * Updates order status by administrator.
     *
     * Allows admin to modify order status such as
     * processing, shipped, delivered, or cancelled.
     *
     * @param orderId unique identifier of order
     * @param orderStatus new order status
     * @return true if status update is successful,
     * otherwise false
     */
    public boolean updateOrderStatus(final int orderId,
                                     final OrderStatus orderStatus) {

        if (orderId <= 0 || orderStatus == null) {
            return false;
        }

        Order order = jdbcOrderRepository.findById(orderId);

        if (order == null) {
            order = memoryOrderRepository.findById(orderId);
        }

        if (order == null) {
            return false;
        }

        order.setOrderStatus(orderStatus);

        boolean jdbcUpdated = jdbcOrderRepository.update(order);

        if (jdbcUpdated) {
            memoryOrderRepository.update(order);
        }

        return jdbcUpdated;
    }
}