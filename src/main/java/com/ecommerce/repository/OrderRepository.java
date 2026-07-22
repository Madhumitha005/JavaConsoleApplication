package com.ecommerce.repository;

import com.ecommerce.model.Order;
import java.util.Collection;

public interface OrderRepository {

    boolean placeOrder(Order order);

    Collection<Order> getOrders();

    Collection<Order> getOrdersByUser(int userId);

    Order findById(int orderId);
}