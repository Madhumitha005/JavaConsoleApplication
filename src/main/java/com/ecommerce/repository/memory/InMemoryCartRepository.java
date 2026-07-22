package com.ecommerce.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

public class InMemoryCartRepository implements CartRepository {

    // In-Memory Storage
    private final Collection<Cart> carts;
    private int nextCartId = 1;

    // Constructor
    public InMemoryCartRepository() {
        this.carts = new ArrayList<>();
    }

    // Add Cart
    @Override
    public boolean addToCart(Cart cart) {

        if (cart == null) {
            return false;
        }

        // One cart per user
        for (Cart existingCart : carts) {

            if (existingCart.getUserId() == cart.getUserId()) {
                return false;
            }
        }

        // Generate Cart ID
        cart.setCartId(nextCartId++);

        return carts.add(cart);
    }

    // Find Cart By User
    @Override
    public Cart findByUserId(int userId) {

        for (Cart cart : carts) {

            if (cart.getUserId() == userId) {
                return cart;
            }
        }

        return null;
    }

    // Get Cart By User
    @Override
    public Collection<Cart> getCartByUser(int userId) {

        Collection<Cart> userCart = new ArrayList<>();

        for (Cart cart : carts) {

            if (cart.getUserId() == userId) {
                userCart.add(cart);
            }
        }

        return userCart;
    }

    // Update Cart
    @Override
    public boolean updateCart(Cart cart) {

        if (cart == null) {
            return false;
        }

        for (Cart existingCart : carts) {

            if (existingCart.getCartId() == cart.getCartId()) {

                existingCart.setUserId(cart.getUserId());
                return true;
            }
        }

        return false;
    }

    // Remove Cart
    @Override
    public boolean removeFromCart(int cartId) {

        Iterator<Cart> iterator = carts.iterator();

        while (iterator.hasNext()) {

            Cart cart = iterator.next();

            if (cart.getCartId() == cartId) {

                iterator.remove();
                return true;
            }
        }

        return false;
    }

    // Clear Cart
    @Override
    public boolean clearCart(int userId) {

        boolean removed = false;

        Iterator<Cart> iterator = carts.iterator();

        while (iterator.hasNext()) {

            Cart cart = iterator.next();

            if (cart.getUserId() == userId) {

                iterator.remove();
                removed = true;
            }
        }

        return removed;
    }
}