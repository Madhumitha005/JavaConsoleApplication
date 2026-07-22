package com.ecommerce.repository;

import com.ecommerce.model.CartItem;
import java.util.Collection;

public interface CartItemRepository {

    boolean addCartItem(CartItem cartItem);

    boolean updateCartItem(CartItem cartItem);

    boolean deleteCartItem(int cartItemId);

    boolean deleteByCartId(int cartId);

    CartItem findById(int cartItemId);

    Collection<CartItem> findByCartId(int cartId);

    Collection<CartItem> findAll();
}