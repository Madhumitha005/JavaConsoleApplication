/*
 * ValidationException.java
 *
 * Version 1.0
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.exception;

public class ValidationException extends RuntimeException {

    /**
     * Constructs an ValidationException with
     * the specified detail message.
     */
    public ValidationException(final String message) {

        super(message);
    }
}