package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;

public class InMemoryOrderItemRepository implements OrderItemRepository {

    private final List<OrderItem> orderItems = new ArrayList<>();
    private int nextId = 1;

    @Override
    public boolean save(OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        orderItem.setOrderItemId(nextId++);
        orderItems.add(orderItem);

        return true;
    }

    @Override
    public OrderItem findById(int orderItemId) {

        for (OrderItem item : orderItems) {

            if (item.getOrderItemId() == orderItemId) {
                return item;
            }
        }

        return null;
    }

    @Override
    public Collection<OrderItem> findByOrderId(int orderId) {

        List<OrderItem> items = new ArrayList<>();

        for (OrderItem item : orderItems) {

            if (item.getOrderId() == orderId) {
                items.add(item);
            }
        }

        return items;
    }

    @Override
    public Collection<OrderItem> findAll() {

        return new ArrayList<>(orderItems);
    }

    @Override
    public boolean update(OrderItem orderItem) {

        for (int i = 0; i < orderItems.size(); i++) {

            if (orderItems.get(i).getOrderItemId() ==
                    orderItem.getOrderItemId()) {

                orderItems.set(i, orderItem);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(int orderItemId) {

        return orderItems.removeIf(item ->
                item.getOrderItemId() == orderItemId);
    }

    @Override
    public boolean deleteByOrderId(int orderId) {

        return orderItems.removeIf(item ->
                item.getOrderId() == orderId);
    }
}