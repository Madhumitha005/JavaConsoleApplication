package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

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

    public static PaymentMethod fromId(final int id) {

        for (PaymentMethod method : values()) {
            if (method.id == id) {
                return method;
            }
        }

        throw new ValidationException("Invalid Payment Method Id");
    }
}