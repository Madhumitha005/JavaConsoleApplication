package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

public enum OrderStatus {

    PENDING(1),
    CONFIRMED(2),
    PACKED(3),
    SHIPPED(4),
    OUT_FOR_DELIVERY(5),
    DELIVERED(6),
    RETURN_REQUESTED(7),
    RETURN_APPROVED(8),
    RETURN_REJECTED(9),
    RETURN_PICKED(10),
    RETURN_COMPLETED(11),
    REFUNDED(12),
    CANCELLED(13);

    private final int id;

    OrderStatus(final int id) {

        this.id = id;
    }

    public int getId() {

        return id;
    }

    // Convert id from DB to Java enum
    public static OrderStatus fromId(final int id) {

        for (OrderStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }

        throw new ValidationException("Invalid Order Status Id");
    }
}