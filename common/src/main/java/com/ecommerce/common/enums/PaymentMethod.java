/*
 * PaymentMethod.java
 *
 * Version 1.1
 *
 * July 29, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

// Represents the supported payment methods
public enum PaymentMethod {

    CASH_ON_DELIVERY(1),
    CREDIT_CARD(2),
    DEBIT_CARD(3),
    UPI(4),
    NET_BANKING(5),
    WALLET(6);

    private final int id;

    PaymentMethod(final int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Converts a database id into the corresponding PaymentMethod enum
    public static PaymentMethod fromId(final int id) {

        for (final PaymentMethod method : values()) {
            if (method.id == id) {
                return method;
            }
        }

        throw new ValidationException("Invalid Payment Method Id");
    }
}