package com.ecommerce.repository;

import java.util.Collection;

import com.ecommerce.model.OrderItem;

public interface OrderItemRepository {

    boolean save(final OrderItem orderItem);

    boolean update(final OrderItem orderItem);

    boolean delete(final int orderItemId);

    boolean deleteByOrderId(final int orderId);

    OrderItem findById(final int orderItemId);

    Collection<OrderItem> findByOrderId(final int orderId);

    Collection<OrderItem> findAll();
}
