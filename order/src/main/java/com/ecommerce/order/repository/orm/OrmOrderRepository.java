package com.ecommerce.order.repository.orm;

import java.util.Collection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.order.entity.Order;
import com.ecommerce.order.repository.OrderRepository;

@Repository
@Transactional
public class OrmOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(Order order) {
        entityManager.persist(order);
        return true;
    }
    @Override
    public boolean update(final Order order) {

        Order existingOrder =
                entityManager.find(Order.class, order.getOrderId());

        if (existingOrder == null) {
            return false;
        }

        existingOrder.setOrderStatus(order.getOrderStatus());
        existingOrder.setPaymentStatus(order.getPaymentStatus());

        return true;
    }
    @Override
    public boolean delete(int orderId) {
        Order entity = entityManager.find(Order.class, orderId);
        if (entity != null) {
            entityManager.remove(entity);
            return true;
        }
        return false;
    }
    @Override
    public Order findById(int orderId) {
        return entityManager.find(Order.class, orderId);
    }
    @Override
    public Collection<Order> findByUserId(int userId) {
        jakarta.persistence.TypedQuery<Order> query = entityManager.createQuery("SELECT e FROM Order e WHERE e.userId = :userId", Order.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    @Override
    public Collection<Order> findBySellerId(int sellerId) {
        jakarta.persistence.TypedQuery<Order> query = entityManager.createQuery("SELECT e FROM Order e WHERE e.sellerId = :sellerId", Order.class);
        query.setParameter("sellerId", sellerId);
        return query.getResultList();
    }
    @Override
    public Collection<Order> findAll() {
        return entityManager.createQuery("SELECT e FROM Order e", Order.class).getResultList();
    }
}
