/*
 * ProductStatus.java
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
 * Represents all possible product statuses.
 */
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

    /**
     * Converts database id into ProductStatus.
     *
     * @param id database id
     * @return ProductStatus
     * @throws ValidationException if id is invalid
     */
    public static ProductStatus fromId (final int id) {

        for (ProductStatus status : values()) {
            if (status.id == id) {
                return status;
            }
        }

        throw new ValidationException("Invalid Product Status Id");
    }
}