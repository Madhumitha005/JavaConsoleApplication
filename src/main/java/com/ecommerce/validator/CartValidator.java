package com.ecommerce.validator;

import com.ecommerce.exception.ValidationException;
import com.ecommerce.model.Cart;

public final class CartValidator {

    private static CartValidator instance;

    private CartValidator() {
    }

    public static CartValidator getInstance() {

        if (instance == null) {
            instance = new CartValidator();
        }
        return instance;
    }

    // Validate Cart
    public void validate(Cart cart) {

        if (cart == null) {
            throw new ValidationException("Cart Cannot Be Null.");
        }
        validateUserId(cart.getUserId());
    }

    // Validate User Id
    public void validateUserId(int userId) {

        if (userId <= 0) {
            throw new ValidationException("Invalid User ID.");
        }
    }

    // Validate Cart Id
    public void validateCartId(int cartId) {

        if (cartId <= 0) {
            throw new ValidationException("Invalid Cart ID.");
        }
    }
}