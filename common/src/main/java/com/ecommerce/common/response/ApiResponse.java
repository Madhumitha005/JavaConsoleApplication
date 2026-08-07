/*
 * ApiResponse.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.common.response;

import java.time.LocalDateTime;

// Represents the standard API response format
public class ApiResponse<T> {

    private boolean success;

    private String message;

    private T data;

    private LocalDateTime timestamp;

    // Default constructor
    public ApiResponse() {

        timestamp = LocalDateTime.now();
    }

    // Parameterized constructor
    public ApiResponse(
            final boolean success,
            final String message,
            final T data) {

        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(final boolean success) {

        this.success = success;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(final String message) {

        this.message = message;
    }

    public T getData() {

        return data;
    }

    public void setData(final T data) {

        this.data = data;
    }

    public LocalDateTime getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(
            final LocalDateTime timestamp) {

        this.timestamp = timestamp;
    }
}