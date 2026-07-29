/*
 * AuthenticationException.java
 *
 * Version 1.0
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.exception;

public class AuthenticationException extends RuntimeException {

    /**
     * Constructs an AuthenticationException with
     * the specified detail message.
     */
    public AuthenticationException(final String message) {
        super(message);
    }
}