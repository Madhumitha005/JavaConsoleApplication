/*
 * ResourceNotFoundException.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.common.exception;

// Thrown when the requested resource cannot be found
public class ResourceNotFoundException
        extends RuntimeException {

    // Creates a ResourceNotFoundException object
    public ResourceNotFoundException(
            final String message) {

        super(message);
    }
}