package com.ecommerce.repository;

import java.util.Collection;

import com.ecommerce.model.Order;

public interface OrderRepository {


    boolean save(final Order order);

    boolean update(final Order order);

    boolean delete(final int orderId);

    Order findById(final int orderId);

    Collection<Order> findByUserId(final int userId);

    Collection<Order> findAll();
}
