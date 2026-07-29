package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

public enum ProductStatus {

    ACTIVE(1),
    INACTIVE(2);

    private final int id;

    ProductStatus(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ProductStatus fromId (final int id) {

        for (ProductStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }

        throw new ValidationException("Invalid Product Status Id");
    }
}