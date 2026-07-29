package com.ecommerce.service;

import com.ecommerce.model.Cart;
import com.ecommerce.repository.CartRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CartService {

    private final CartRepository jdbcRepository;

    public CartService(

            @Qualifier("jdbcCartRepository")
            final CartRepository jdbcRepository) {

        this.jdbcRepository = jdbcRepository;
    }


    // Save Cart
    public boolean save(final Cart cart) {

        if (cart == null) {

            return false;
        }

        return jdbcRepository.save(cart);
    }


    // Find Cart by Id
    public Cart findById(final int cartId) {

        if(cartId <= 0) {

            return null;
        }

        return jdbcRepository.findById(cartId);
    }

    // Find Cart by User
    public Cart findByUserId(final int userId) {

        if(userId <= 0) {

            return null;
        }

        return jdbcRepository.findByUserId(userId);
    }

    // View User Cart
    public Collection<Cart> findByUserIdList(final int userId) {

        if(userId <= 0) {

            return Collections.emptyList();
        }

        return jdbcRepository.findByUserIdList(userId);
    }

    // View All Cart
    public Collection<Cart> findAll() {

        return jdbcRepository.findAll();
    }

    // Update Cart
    public boolean update(final Cart cart) {

        if(cart == null) {

            return false;
        }

        return jdbcRepository.update(cart);
    }

    // Delete Cart
    public boolean delete(final int cartId) {

        if(cartId <= 0) {

            return false;
        }

        return jdbcRepository.delete(cartId);
    }
}