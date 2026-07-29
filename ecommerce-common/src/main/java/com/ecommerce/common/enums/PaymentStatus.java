/*
 * PaymentStatus.java
 *
 * Version 1.0
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

/**
 * Represents the different payment statuses
 * in the E-Commerce System.
 */
public enum PaymentStatus {

    PENDING(1),
    SUCCESS(2),
    FAILED(3),
    REFUNDED(4),
    CANCELLED(5);

    private final int id;

    PaymentStatus(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Converts a database id into the corresponding
     * PaymentStatus enum.
     *
     * @param id payment status id
     * @return matching PaymentStatus
     * @throws ValidationException if the id is invalid
     */
    public static PaymentStatus fromId(final int id) {

        for (PaymentStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }

        throw new ValidationException("Invalid Payment Status Id");
    }
}