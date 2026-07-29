package com.ecommerce.common.enums;

import com.ecommerce.common.exception.ValidationException;

public enum Role {

    ADMIN(1),
    CUSTOMER(2);

    private final int id;

    Role(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role fromId(final int id) {

        for (Role role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new ValidationException("Invalid Role Id");
    }
}