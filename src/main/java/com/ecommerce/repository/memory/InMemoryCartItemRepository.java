package com.ecommerce.repository.memory;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InMemoryCartItemRepository implements CartItemRepository {

    private final List<CartItem> cartItems = new ArrayList<>();

    @Override
    public boolean addCartItem(CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        cartItem.setCartItemId(cartItems.size() + 1);
        cartItems.add(cartItem);

        return true;
    }

    @Override
    public boolean updateCartItem(CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        for (int i = 0; i < cartItems.size(); i++) {

            if (cartItems.get(i).getCartItemId() == cartItem.getCartItemId()) {

                cartItems.set(i, cartItem);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean deleteCartItem(int cartItemId) {

        return cartItems.removeIf(
                item -> item.getCartItemId() == cartItemId
        );
    }

    @Override
    public boolean deleteByCartId(int cartId){

        return cartItems.removeIf(
            item -> item.getCartId() == cartId
        );
    }

    @Override
    public CartItem findById(int cartItemId) {

        for (CartItem item : cartItems) {

            if (item.getCartItemId() == cartItemId) {
                return item;
            }
        }

        return null;
    }

    @Override
    public Collection<CartItem> findByCartId(int cartId) {

        Collection<CartItem> items = new ArrayList<>();

        for (CartItem item : cartItems) {

            if (item.getCartId() == cartId) {
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