package com.ecommerce.repository.memory;

import com.ecommerce.model.CartItem;
import com.ecommerce.repository.CartItemRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository("inMemoryCartItemRepository")
public class InMemoryCartItemRepository
        implements CartItemRepository {

    private final List<CartItem> cartItems = new ArrayList<>();

    @Override
    public boolean save(final CartItem cartItem) {

        if (cartItem == null) {
            return false;
        }

        cartItem.setCartItemId(cartItems.size() + 1);
        return cartItems.add(cartItem);
    }

    @Override
    public boolean update(final CartItem cartItem) {

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
    public boolean delete(final int cartItemId) {

        return cartItems.removeIf(
                item -> item.getCartItemId() == cartItemId
        );
    }

    @Override
    public boolean deleteByCartId(final int cartId) {

        return cartItems.removeIf(
                item -> item.getCartId() == cartId
        );
    }

    @Override
    public CartItem findById(final int cartItemId) {

        for (final CartItem item : cartItems) {

            if (item.getCartItemId() == cartItemId) {
                return item;
            }
        }

        return null;
    }

    @Override
    public Collection<CartItem> findByCartId(final int cartId) {

        final Collection<CartItem> items = new ArrayList<>();

        for (final CartItem item : cartItems) {

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