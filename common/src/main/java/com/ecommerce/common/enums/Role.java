/*
 * Role.java
 *
 * Version 1.0
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

// Represents user roles in the ecommerce system
public enum Role {

    ADMIN(1),
    CUSTOMER(2),
    SELLER(3);

    private final int id;

    Role(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    // Converts a database role id into a Role enum
    public static Role fromId(final int id) {

        for (final Role role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new ValidationException("Invalid Role Id");
    }
}