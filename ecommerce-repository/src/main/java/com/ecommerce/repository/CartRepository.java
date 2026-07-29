package com.ecommerce.repository;

import com.ecommerce.model.Cart;
import java.util.Collection;

public interface CartRepository {

    boolean save(final Cart cart);

    boolean update(final Cart cart);

    boolean delete(final int cartId);

    Cart findById(final int cartId);

    Cart findByUserId(final int userId);

    Collection<Cart> findAll();

    Collection<Cart> findByUserIdList(final int userId);
}