package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CartService {

    private final CartRepository memoryRepository;
    private final CartRepository jdbcRepository;

    public CartService(

            @Qualifier("inMemoryCartRepository")
            final CartRepository memoryRepository,

            @Qualifier("jdbcCartRepository")
            final CartRepository jdbcRepository) {

        this.memoryRepository = memoryRepository;
        this.jdbcRepository = jdbcRepository;
    }


    // Save Cart
    public boolean save(final Cart cart) {

        if (cart == null) {

            return false;
        }

        boolean memorySaved = memoryRepository.save(cart);
        boolean jdbcSaved = jdbcRepository.save(cart);

        return memorySaved && jdbcSaved;
    }


    // Find Cart by Id
    public Cart findById(final int cartId) {

        if (cartId <= 0) {

            return null;
        }

        Cart cart = memoryRepository.findById(cartId);

        if (cart == null) {
            cart = jdbcRepository.findById(cartId);
        }

        return cart;
    }

    // Find Cart by User
    public Cart findByUserId(final int userId) {

        if(userId <= 0) {

            return null;
        }

        Cart cart = memoryRepository.findByUserId(userId);

        if (cart == null) {
            cart = jdbcRepository.findByUserId(userId);
        }

        return cart;
    }

    // View User Cart
    public Collection<Cart> findByUserIdList(final int userId) {

        if(userId <= 0) {

            return Collections.emptyList();
        }


        Collection<Cart> carts = memoryRepository.findByUserIdList(userId);

        if (carts == null || carts.isEmpty()) {
            carts = jdbcRepository.findByUserIdList(userId);
        }

        return carts;
    }

    // View All Cart
    public Collection<Cart> findAll() {

        Collection<Cart> carts = memoryRepository.findAll();

        if (carts == null || carts.isEmpty()) {
            carts = jdbcRepository.findAll();
        }

        return carts;
    }

    // Update Cart
    public boolean update(final Cart cart) {

        if(cart == null) {

            return false;
        }

        boolean memoryUpdated = memoryRepository.update(cart);
        boolean jdbcUpdated = jdbcRepository.update(cart);

        return memoryUpdated && jdbcUpdated;
    }

    // Delete Cart
    public boolean delete(final int cartId) {

        if(cartId <= 0) {

            return false;
        }

        boolean memoryDeleted = memoryRepository.delete(cartId);
        boolean jdbcDeleted = jdbcRepository.delete(cartId);

        return memoryDeleted && jdbcDeleted;
    }
}