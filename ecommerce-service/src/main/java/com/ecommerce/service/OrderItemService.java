package com.ecommerce.service;

import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderItemService {

    private final OrderItemRepository memoryRepository;
    private final OrderItemRepository jdbcRepository;

    public OrderItemService(

            @Qualifier("inMemoryOrderItemRepository")
            final OrderItemRepository memoryRepository,

            @Qualifier("jdbcOrderItemRepository")
            final OrderItemRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }

    public boolean addOrderItem(final OrderItem orderItem) {

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

        if (orderItem.getPrice() <= 0) {
            return false;
        }

        boolean jdbcSaved = jdbcRepository.save(orderItem);

        if (!jdbcSaved) {
            return false;
        }

        return memoryRepository.save(orderItem);
    }

    public boolean addOrderItems(final Collection<OrderItem> orderItems) {

        if (orderItems == null || orderItems.isEmpty()) {

            return false;
        }

        for (final OrderItem orderItem : orderItems) {

            boolean saved = addOrderItem(orderItem);

            if (!saved) {
                return false;
            }
        }

        return true;
    }

    public OrderItem getOrderItemById(final int orderItemId) {

        if (orderItemId <= 0) {
            return null;
        }

        OrderItem orderItem = jdbcRepository.findById(orderItemId);

        if (orderItem == null) {

            orderItem = memoryRepository.findById(orderItemId);
        }
        return orderItem;
    }

    public Collection<OrderItem> getOrderItemsByOrderId(final int orderId) {

        if (orderId <= 0) {

            return java.util.Collections.emptyList();
        }

        return jdbcRepository.findByOrderId(orderId);
    }

    public Collection<OrderItem> getAllOrderItems() {

        return jdbcRepository.findAll();
    }

    public boolean updateOrderItem(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderItemId() <= 0) {
            return false;
        }

        boolean jdbcUpdated = jdbcRepository.update(orderItem);

        if (!jdbcUpdated) {
            return false;
        }

        memoryRepository.update(orderItem);

        return true;
    }

    public boolean deleteOrderItem(final int orderItemId) {

        if (orderItemId <= 0) {
            return false;
        }

        boolean jdbcDeleted = jdbcRepository.delete(orderItemId);

        if (!jdbcDeleted) {
            return false;
        }

        memoryRepository.delete(orderItemId);

        return true;
    }

    public boolean deleteOrderItemsByOrderId(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        boolean jdbcDeleted = jdbcRepository.deleteByOrderId(orderId);
        memoryRepository.deleteByOrderId(orderId);

        return jdbcDeleted ;
    }
}