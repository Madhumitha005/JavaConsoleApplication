package com.ecommerce.enumtype;

import com.ecommerce.exception.ValidationException;

public enum Role {

    ADMIN(1),
    CUSTOMER(2);

    private final int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role fromId(int id) {

        for (Role role : values()) {
            if (role.id == id) {
                return role;
            }
        }
        throw new ValidationException("Invalid Role Id");
    }
}