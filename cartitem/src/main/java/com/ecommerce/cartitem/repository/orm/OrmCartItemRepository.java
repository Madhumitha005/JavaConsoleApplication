package com.ecommerce.cartitem.repository.orm;

import java.util.Collection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.ecommerce.cartitem.entity.CartItem;
import com.ecommerce.cartitem.repository.CartItemRepository;

@Repository
@Transactional
public class OrmCartItemRepository implements CartItemRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean save(final CartItem cartItem) {

        entityManager.persist(cartItem);
        return true;
    }

    @Override
    public boolean update(final CartItem cartItem) {

        CartItem existing = entityManager.find(CartItem.class, cartItem.getCartItemId());

        if (existing == null) {
            return false;
        }
        existing.setUserId(cartItem.getUserId());
        existing.setProductId(cartItem.getProductId());
        existing.setQuantity(cartItem.getQuantity());
        return true;
    }

    @Override
    public boolean delete(final int cartItemId) {

        CartItem entity = entityManager.find(CartItem.class, cartItemId);

        if (entity == null) {
            return false;
        }
        entityManager.remove(entity);
        return true;
    }

    @Override
    public boolean deleteByUserId(final int userId) {

        int deletedCount =
                entityManager.createQuery(
                                "DELETE FROM CartItem c "
                                        + "WHERE c.userId = :userId"
                        )
                        .setParameter("userId", userId)
                        .executeUpdate();

        return deletedCount > 0;
    }

    @Override
    public CartItem findById(final int cartItemId) {

        return entityManager.find(CartItem.class, cartItemId);
    }

    @Override
    public Collection<CartItem> findByUserId(final int userId) {

        TypedQuery<CartItem> query =
                entityManager.createQuery(
                        "SELECT c "
                                + "FROM CartItem c "
                                + "WHERE c.userId = :userId",
                        CartItem.class
                );
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public Collection<CartItem> findAll() {

        return entityManager.createQuery(
                "SELECT c FROM CartItem c",
                CartItem.class
        ).getResultList();
    }
}