package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;

import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;

public class InMemoryOrderRepository implements OrderRepository {

    // Store in memroy
    private final Collection<Order> orders;

    public InMemoryOrderRepository() {
        this.orders = new ArrayList<>();
    }

    @Override
    public boolean placeOrder(Order order) {

        if (order == null) {
            return false;
        }
        return orders.add(order);
    }

    @Override
    public Collection<Order> getOrders() {

        return new ArrayList<>(orders);
    }

    @Override
    public Collection<Order> getOrdersByUser(int userId) {

        Collection<Order> userOrders = new ArrayList<>();

        for (Order order : orders) {

            if (order.getUserId() == userId) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public Order findById(int orderId) {

        for (Order order : orders) {

            if (order.getOrderId() == orderId) {
                return order;
            }
        }
        return null;
    }
}