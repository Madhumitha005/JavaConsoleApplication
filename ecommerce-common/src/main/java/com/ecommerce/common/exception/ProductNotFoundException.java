package com.ecommerce.common.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(final String message) {
        super(message);
    }
}