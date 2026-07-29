package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

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

    public static PaymentStatus fromId(final int id) {

        for (PaymentStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }

        throw new ValidationException("Invalid Payment Status Id");
    }
}