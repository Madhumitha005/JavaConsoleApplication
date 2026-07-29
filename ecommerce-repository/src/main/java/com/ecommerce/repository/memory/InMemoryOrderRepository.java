package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.Order;
import com.ecommerce.repository.OrderRepository;

@Repository("inMemoryOrderRepository")
public class InMemoryOrderRepository
        implements OrderRepository {

    private final Collection<Order> orders;

    public InMemoryOrderRepository() {

        this.orders = new ArrayList<>();
    }

    @Override
    public boolean save(final Order order) {

        if (order == null) {
            return false;
        }

        if (order.getUserId() <= 0) {
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

        if (order.getTotalAmount() <= 0) {
            return false;
        }

        order.setOrderId(IdGenerator.getInstance().nextOrderId());

        return orders.add(order);
    }

    @Override
    public boolean update(final Order order) {

        if (order == null) {
            return false;
        }

        if (order.getOrderId() <= 0) {
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

        if (order.getTotalAmount() <= 0) {
            return false;
        }

        for (Order existingOrder : orders) {

            if (existingOrder.getOrderId() == order.getOrderId()) {

                existingOrder.setUserId(order.getUserId());
                existingOrder.setCustomerName(order.getCustomerName());
                existingOrder.setPhone(order.getPhone());
                existingOrder.setAddress(order.getAddress());
                existingOrder.setTotalAmount(order.getTotalAmount());

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        return orders.removeIf(order -> order.getOrderId() == orderId);
    }

    @Override
    public Order findById(final int orderId) {

        if (orderId <= 0) {
            return null;
        }

        for (Order order : orders) {

            if (order.getOrderId() == orderId) {

                return order;
            }
        }
        return null;
    }

    @Override
    public Collection<Order> findByUserId(final int userId) {

        Collection<Order> userOrders = new ArrayList<>();

        if (userId <= 0) {
            return userOrders;
        }

        for (Order order : orders) {

            if (order.getUserId() == userId) {

                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public Collection<Order> findAll() {

        return new ArrayList<>(orders);
    }
}
