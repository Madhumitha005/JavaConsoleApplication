/*
 * InMemoryCartItemRepository.java
 *
 * Version 1.0
 *
 * August 03, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.cartitem.repository.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ecommerce.common.util.IdGenerator;
import com.ecommerce.cartitem.entity.CartItem;
import com.ecommerce.cartitem.repository.CartItemRepository;

@Repository("inMemoryCartItemRepository")
public class InMemoryCartItemRepository implements CartItemRepository {

    private final List<CartItem> cartItems;

    public InMemoryCartItemRepository() {

        this.cartItems = new ArrayList<>();
    }

    @Override
    public boolean save(final CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        if (cartItem.getUserId() <= 0) {
            return false;
        }

        if (cartItem.getProductId() <= 0) {
            return false;
        }

        if (cartItem.getQuantity() <= 0) {
            return false;
        }

        cartItem.setCartItemId(IdGenerator.getInstance().nextCartItemId());
        return cartItems.add(cartItem);
    }

    @Override
    public boolean update(final CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        for (int index = 0; index < cartItems.size(); index++) {

            if (cartItems.get(index).getCartItemId()
                    == cartItem.getCartItemId()) {

                cartItems.set(index, cartItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(final int cartItemId) {

        return cartItems.removeIf(item -> item.getCartItemId() == cartItemId);
    }

    @Override
    public boolean deleteByUserId(final int userId) {

        return cartItems.removeIf(item -> item.getUserId() == userId);
    }

    @Override
    public CartItem findById(final int cartItemId) {

        for (CartItem item : cartItems) {

            if (item.getCartItemId() == cartItemId) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Collection<CartItem> findByUserId(final int userId) {

        Collection<CartItem> items = new ArrayList<>();

        for (CartItem item : cartItems) {

            if (item.getUserId() == userId) {

                items.add(item);
            }
        }
        return items;
    }

    @Override
    public Collection<CartItem> findAll() {

        return new ArrayList<>(cartItems);
    }
}