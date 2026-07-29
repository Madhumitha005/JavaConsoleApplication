package com.ecommerce.repository.memory;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;
import com.ecommerce.common.util.IdGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository("inMemoryCartRepository")
public class InMemoryCartRepository implements CartRepository {


    private final List<Cart> carts = new ArrayList<>();


    @Override
    public boolean save(final Cart cart) {

        if (cart == null) {
            return false;
        }

        cart.setCartId(
                IdGenerator.getInstance().nextCartId()
        );

        return carts.add(cart);
    }


    @Override
    public boolean update(final Cart cart) {

        if (cart == null) {
            return false;
        }


        for (int i = 0; i < carts.size(); i++) {

            if (carts.get(i).getCartId()
                    == cart.getCartId()) {


                carts.set(i, cart);

                return true;
            }
        }

        return false;
    }


    @Override
    public boolean delete(final int cartId) {

        return carts.removeIf(
                cart -> cart.getCartId() == cartId
        );
    }


    @Override
    public Cart findById(final int cartId) {


        for (final Cart cart : carts) {

            if (cart.getCartId() == cartId) {

                return cart;
            }
        }

        return null;
    }



    @Override
    public Cart findByUserId(final int userId) {


        for (final Cart cart : carts) {

            if (cart.getUserId() == userId) {

                return cart;
            }
        }

        return null;
    }



    @Override
    public Collection<Cart> findAll() {

        return new ArrayList<>(carts);
    }



    @Override
    public Collection<Cart> findByUserIdList(final int userId) {


        Collection<Cart> result = new ArrayList<>();


        for (final Cart cart : carts) {

            if (cart.getUserId() == userId) {

                result.add(cart);
            }
        }


        return result;
    }
}