/*
 * OrderService.java
 *
 * Version 2.0
 *
 * August 21, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.order.service;

import java.util.Collection;
import java.util.Objects;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.order.entity.Order;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.repository.OrderItemRepository;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public OrderService(
            final OrderRepository orderRepository,
            final OrderItemRepository orderItemRepository,
            final ProductRepository productRepository) {

        this.orderRepository =
                Objects.requireNonNull(orderRepository);

        this.orderItemRepository =
                Objects.requireNonNull(orderItemRepository);

        this.productRepository =
                Objects.requireNonNull(productRepository);
    }

    // Places new order
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "ordersByUser",
                    key = "#order.userId"),
            @CacheEvict(value = "ordersBySeller",
                    key = "#order.sellerId")
    })
    public boolean placeOrder(
            final Order order,
            final Collection<OrderItem> orderItems) {

        if (order == null || orderItems == null || orderItems.isEmpty()) {
            return false;
        }

        double totalAmount = 0.0;

        for (OrderItem item : orderItems) {

            Product product =
                    productRepository.findById(item.getProductId());

            if (product == null) {
                return false;
            }

            if (product.getQuantity() < item.getQuantity()) {
                return false;
            }

            if (order.getSellerId() == null) {

                order.setSellerId(product.getSeller().getId());

            } else if (!order.getSellerId()
                    .equals(product.getSeller().getId())) {

                return false;
            }

            double price = product.getPrice();
            double discount = product.getDiscount();
            double tax = product.getTax();

            double discountAmount =
                    price * discount / 100;

            double afterDiscount =
                    price - discountAmount;

            double taxAmount =
                    afterDiscount * tax / 100;

            double finalPrice =
                    afterDiscount + taxAmount;

            totalAmount +=
                    finalPrice * item.getQuantity();

            product.setQuantity(
                    product.getQuantity()
                            - item.getQuantity());

            productRepository.update(product);
        }

        order.setTotalAmount(totalAmount);

        if (order.getOrderStatus() == null) {
            order.setOrderStatus(OrderStatus.PENDING);
        }

        if (order.getPaymentStatus() == null) {
            order.setPaymentStatus(PaymentStatus.PENDING);
        }

        boolean saved =
                orderRepository.save(order);

        if (!saved) {
            return false;
        }

        for (OrderItem item : orderItems) {

            item.setOrderId(order.getOrderId());

            orderItemRepository.save(item);
        }

        return true;
    }

    // Find order by ID
    @Cacheable(
            value = "ordersById",
            key = "#orderId")
    public Order findById(
            final Integer orderId) {

        if (orderId == null) {
            return null;
        }

        return orderRepository.findById(orderId);
    }

    // Find all orders
    @Cacheable(
            value = "orders",
            key = "'all'")
    public Collection<Order> findAll() {

        return orderRepository.findAll();
    }

    // Find orders by user
    @Cacheable(
            value = "ordersByUser",
            key = "#userId")
    public Collection<Order> findByUserId(
            final Integer userId) {

        return orderRepository.findByUserId(userId);
    }

    // Find orders by seller
    @Cacheable(
            value = "ordersBySeller",
            key = "#sellerId")
    public Collection<Order> findBySellerId(
            final Integer sellerId) {

        return orderRepository.findBySellerId(sellerId);
    }

    // Update order
    @Caching(evict = {
            @CacheEvict(
                    value = "orders",
                    allEntries = true),

            @CacheEvict(
                    value = "ordersById",
                    key = "#order.orderId"),

            @CacheEvict(
                    value = "ordersByUser",
                    allEntries = true),

            @CacheEvict(
                    value = "ordersBySeller",
                    allEntries = true)
    })
    public boolean update(
            final Order order) {

        if (order == null ||
                order.getOrderId() == null) {

            return false;
        }

        /*
         * Important:
         * Never merge a newly-created partial Order.
         * First load the existing entity from DB.
         */
        Order existingOrder =
                orderRepository.findById(
                        order.getOrderId());

        if (existingOrder == null) {
            return false;
        }

        if (order.getOrderStatus() != null) {

            existingOrder.setOrderStatus(
                    order.getOrderStatus());
        }

        if (order.getPaymentStatus() != null) {

            existingOrder.setPaymentStatus(
                    order.getPaymentStatus());
        }

        return orderRepository.update(
                existingOrder);
    }

    // Update order status
    @Caching(evict = {
            @CacheEvict(
                    value = "orders",
                    allEntries = true),

            @CacheEvict(
                    value = "ordersById",
                    key = "#orderId"),

            @CacheEvict(
                    value = "ordersByUser",
                    allEntries = true),

            @CacheEvict(
                    value = "ordersBySeller",
                    allEntries = true)
    })
    public boolean updateOrderStatus(
            final Integer orderId,
            final OrderStatus orderStatus) {

        if (orderId == null ||
                orderStatus == null) {

            return false;
        }

        Order order =
                orderRepository.findById(orderId);

        if (order == null) {
            return false;
        }

        order.setOrderStatus(orderStatus);

        return orderRepository.update(order);
    }

    // Delete order
    @Caching(evict = {
            @CacheEvict(
                    value = "orders",
                    allEntries = true),

            @CacheEvict(
                    value = "ordersById",
                    key = "#orderId"),

            @CacheEvict(
                    value = "ordersByUser",
                    allEntries = true),

            @CacheEvict(
                    value = "ordersBySeller",
                    allEntries = true)
    })
    public boolean delete(
            final Integer orderId) {

        if (orderId == null) {
            return false;
        }

        return orderRepository.delete(orderId);
    }
}