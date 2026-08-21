package com.ecommerce.orderitem.repository.orm;

import java.util.Collection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.orderitem.entity.OrderItem;
import com.ecommerce.orderitem.repository.OrderItemRepository;

@Repository
@Transactional
public class OrmOrderItemRepository implements OrderItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(final OrderItem orderItem) {

        if (orderItem == null) {
            return false;
        }

        entityManager.persist(orderItem);

        return true;
    }

    @Override
    public boolean update(final OrderItem orderItem) {

        if (orderItem == null
                || orderItem.getOrderItemId() <= 0) {

            return false;
        }

        OrderItem existing =
                entityManager.find(
                        OrderItem.class,
                        orderItem.getOrderItemId());

        if (existing == null) {
            return false;
        }

        existing.setQuantity(orderItem.getQuantity());

        return true;
    }

    @Override
    public boolean delete(final int orderItemId) {

        OrderItem entity =
                entityManager.find(OrderItem.class, orderItemId);

        if (entity == null) {
            return false;
        }

        entityManager.remove(entity);

        return true;
    }

    @Override
    public boolean deleteByOrderId(final int orderId) {

        int deletedCount =
                entityManager.createQuery(
                                "DELETE FROM OrderItem e " +
                                        "WHERE e.orderId = :orderId")
                        .setParameter("orderId", orderId)
                        .executeUpdate();

        return deletedCount > 0;
    }

    @Override
    public OrderItem findById(final int orderItemId) {

        return entityManager.find(
                OrderItem.class,
                orderItemId);
    }

    @Override
    public Collection<OrderItem> findByOrderId(
            final int orderId) {

        return entityManager.createQuery(
                        "SELECT e FROM OrderItem e " +
                                "WHERE e.orderId = :orderId",
                        OrderItem.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    @Override
    public Collection<OrderItem> findAll() {

        return entityManager.createQuery(
                        "SELECT e FROM OrderItem e",
                        OrderItem.class)
                .getResultList();
    }
}