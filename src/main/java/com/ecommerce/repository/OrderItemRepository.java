package com.ecommerce.repository;

import com.ecommerce.model.OrderItem;

import java.util.Collection;

public interface OrderItemRepository {

    boolean save(OrderItem orderItem);

    OrderItem findById(int orderItemId);

    Collection<OrderItem> findByOrderId(int orderId);

    Collection<OrderItem> findAll();

    boolean update(OrderItem orderItem);

    boolean delete(int orderItemId);

    boolean deleteByOrderId(int orderId);
}