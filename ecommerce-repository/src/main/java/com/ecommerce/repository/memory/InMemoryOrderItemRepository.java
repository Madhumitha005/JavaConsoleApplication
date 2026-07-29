package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.model.OrderItem;
import com.ecommerce.repository.OrderItemRepository;

@Repository("inMemoryOrderItemRepository")
public class InMemoryOrderItemRepository
        implements OrderItemRepository {
    
    private final List<OrderItem> orderItems;

    public InMemoryOrderItemRepository() {

        this.orderItems = new ArrayList<>();
    }

    @Override
    public boolean save(final OrderItem orderItem) {

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

        orderItem.setOrderItemId(IdGenerator.getInstance().nextOrderItemId());

        return orderItems.add(orderItem);
    }

    @Override
    public boolean update(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        if (orderItem.getOrderItemId() <= 0) {
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

        for (int index = 0; index < orderItems.size(); index++) {

            OrderItem existingOrderItem = orderItems.get(index);

            if (existingOrderItem.getOrderItemId() == orderItem.getOrderItemId()) {

                orderItems.set(index, orderItem);

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(final int orderItemId) {

        if (orderItemId <= 0) {
            return false;
        }

        return orderItems.removeIf(item -> item.getOrderItemId() == orderItemId);
    }

    @Override
    public boolean deleteByOrderId(final int orderId) {

        if (orderId <= 0) {
            return false;
        }

        return orderItems.removeIf(item -> item.getOrderId() == orderId);
    }

    @Override
    public OrderItem findById(final int orderItemId) {

        if (orderItemId <= 0) {
            return null;
        }

        for (OrderItem item : orderItems) {

            if (item.getOrderItemId() == orderItemId) {

                return item;
            }
        }

        return null;
    }

    @Override
    public Collection<OrderItem> findByOrderId(final int orderId) {

        Collection<OrderItem> result = new ArrayList<>();

        if (orderId <= 0) {
            return result;
        }

        for (OrderItem item : orderItems) {

            if (item.getOrderId() == orderId) {

                result.add(item);
            }
        }

        return result;
    }

    @Override
    public Collection<OrderItem> findAll() {

        return new ArrayList<>(orderItems);
    }
}
