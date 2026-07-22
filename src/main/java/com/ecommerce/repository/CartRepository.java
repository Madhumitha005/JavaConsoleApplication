package com.ecommerce.repository;

import java.util.Collection;

import com.ecommerce.model.Cart;

public interface CartRepository {

    boolean addToCart(Cart cart);

    // NEW
    Cart findByUserId(int userId);

    Collection<Cart> getCartByUser(int userId);

    boolean updateCart(Cart cart);

    boolean removeFromCart(int cartId);

    boolean clearCart(int userId);
}