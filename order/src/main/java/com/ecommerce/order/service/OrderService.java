/*
 * OrderService.java
 *
 * Version 1.7
 *
 * August 05, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.order.service;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;

import com.ecommerce.common.enums.OrderStatus;
import com.ecommerce.common.enums.PaymentStatus;
import com.ecommerce.order.entity.Order;
import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.repository.OrderItemRepository;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.repository.ProductRepository;

// Service class responsible for order operations
@Service
@Transactional
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

        this.memoryOrderRepository = Objects.requireNonNull(memoryOrderRepository);
        this.jdbcOrderRepository = Objects.requireNonNull(jdbcOrderRepository);
        this.memoryOrderItemRepository = Objects.requireNonNull(memoryOrderItemRepository);
        this.jdbcOrderItemRepository = Objects.requireNonNull(jdbcOrderItemRepository);
        this.memoryProductRepository = Objects.requireNonNull(memoryProductRepository);
        this.jdbcProductRepository = Objects.requireNonNull(jdbcProductRepository);
    }

    // Places new order
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "ordersByUser", key = "#order.userId"),
            @CacheEvict(value = "ordersBySeller", key = "#order.sellerId")
    })
    public boolean placeOrder(
            final Order order,
            final Collection<OrderItem> orderItems) {

        if (order == null || orderItems == null) {

            return false;
        }

        double totalAmount = 0.0;

        for (OrderItem item : orderItems) {

            Product product = jdbcProductRepository.findById(item.getProductId());

            if (product == null) {

                product = memoryProductRepository.findById(item.getProductId());
            }

            if (product == null) {

                return false;
            }

            Integer sellerId = product.getSeller().getId();

            if (order.getSellerId() == null) {

                order.setSellerId(sellerId);

            } else if (!order.getSellerId().equals(sellerId)) {

                return false;
            }

            if (product.getQuantity() < item.getQuantity()) {

                return false;
            }

            double price = product.getPrice();
            double discount = product.getDiscount();
            double tax = product.getTax();
            double discountAmount = price * discount / 100;
            double afterDiscount = price - discountAmount;
            double taxAmount = afterDiscount * tax / 100;
            double finalPrice = afterDiscount + taxAmount;

            totalAmount += finalPrice * item.getQuantity();

            product.setQuantity(product.getQuantity() - item.getQuantity());

            jdbcProductRepository.update(product);
            memoryProductRepository.update(product);
        }

        order.setTotalAmount(totalAmount);

        if (order.getOrderStatus() == null) {

            order.setOrderStatus(OrderStatus.PENDING);
        }

        if (order.getPaymentStatus() == null) {

            order.setPaymentStatus(PaymentStatus.PENDING);
        }

        boolean saved = jdbcOrderRepository.save(order);

        if (!saved) {

            return false;
        }

        memoryOrderRepository.save(order);

        for (OrderItem item : orderItems) {

            item.setOrderId(order.getOrderId());

            jdbcOrderItemRepository.save(item);
            memoryOrderItemRepository.save(item);
        }

        return true;
    }

    // Find order by id
    @Cacheable(value = "ordersById", key = "#orderId")
    public Order findById(final Integer orderId) {

        Order order = memoryOrderRepository.findById(orderId);

        if (order == null) {

            order = jdbcOrderRepository.findById(orderId);
        }

        return order;
    }

    // Find all orders
    @Cacheable(value = "orders", key = "'all'")
    public Collection<Order> findAll() {

        return jdbcOrderRepository.findAll();
    }

    // Find orders by user
    @Cacheable(value = "ordersByUser", key = "#userId")
    public Collection<Order> findByUserId(final Integer userId) {

        Collection<Order> orders = memoryOrderRepository.findByUserId(userId);

        if (orders == null || orders.isEmpty()) {

            orders = jdbcOrderRepository.findByUserId(userId);
        }

        return orders;
    }

    // Update order
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "ordersById", key = "#order.orderId"),
            @CacheEvict(value = "ordersByUser", allEntries = true),
            @CacheEvict(value = "ordersBySeller", allEntries = true)
    })
    public boolean update(final Order order) {

        boolean updated = jdbcOrderRepository.update(order);

        if (updated) {

            memoryOrderRepository.update(order);
        }

        return updated;
    }

    // Update order status
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "ordersById", key = "#orderId"),
            @CacheEvict(value = "ordersByUser", allEntries = true),
            @CacheEvict(value = "ordersBySeller", allEntries = true)
    })
    public boolean updateOrderStatus(
            final Integer orderId,
            final OrderStatus orderStatus) {

        Order order = findById(orderId);

        if (order == null) {
            return false;
        }

        order.setOrderStatus(orderStatus);

        return update(order);
    }

    // Delete order
    @Caching(evict = {
            @CacheEvict(value = "orders", allEntries = true),
            @CacheEvict(value = "ordersById", key = "#orderId"),
            @CacheEvict(value = "ordersByUser", allEntries = true),
            @CacheEvict(value = "ordersBySeller", allEntries = true)
    })
    public boolean delete(final Integer orderId) {

        boolean deleted = jdbcOrderRepository.delete(orderId);

        if (deleted) {

            memoryOrderRepository.delete(orderId);
        }

        return deleted;
    }

    @Cacheable(value = "ordersBySeller", key = "#sellerId")
    public Collection<Order> findBySellerId(
            final Integer sellerId) {

        Collection<Order> orders =
                memoryOrderRepository.findBySellerId(sellerId);

        if (orders == null || orders.isEmpty()) {

            orders = jdbcOrderRepository.findBySellerId(sellerId);
        }

        return orders;
    }
}