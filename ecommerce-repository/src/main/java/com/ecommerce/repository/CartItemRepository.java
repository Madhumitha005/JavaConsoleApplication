package com.ecommerce.repository;

import com.ecommerce.model.CartItem;
import java.util.Collection;

public interface CartItemRepository {

    boolean save(final CartItem cartItem);

    boolean update(final CartItem cartItem);

    boolean delete(final int cartItemId);

    boolean deleteByCartId(final int cartId);

    CartItem findById(final int cartItemId);

    Collection<CartItem> findByCartId(final int cartId);

    Collection<CartItem> findAll();
}