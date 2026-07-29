/*
 * PasswordUtil.java
 *
 * Version 1.0
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.util;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Provides password hashing and password
 * verification utilities.
 *
 *
 * This class follows the Singleton design
 * pattern to ensure that only one instance
 * exists throughout the application.
 *
 */
@Component
public final class PasswordUtil {

    private static PasswordUtil instance;

    private PasswordUtil() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return PasswordUtil instance
     */
    public static PasswordUtil getInstance() {

        if (instance == null) {
            instance = new PasswordUtil();
        }

        return instance;
    }

    /**
     * Generates a hash value for the given password.
     *
     * @param password plain text password
     * @return hashed password
     * @throws NullPointerException if password is null
     */
    public String hash(final String password) {

        Objects.requireNonNull(password, "Password cannot be null.");

        return String.valueOf(password.hashCode());
    }

    /**
     * Verifies whether the raw password matches
     * the hashed password.
     *
     * @param rawPassword plain text password
     * @param hashedPassword stored hashed password
     * @return true if passwords match, otherwise false
     * @throws NullPointerException if any argument is null
     */
    public boolean matches(final String rawPassword, final String hashedPassword) {

        Objects.requireNonNull(rawPassword, "Password cannot be null.");
        Objects.requireNonNull(hashedPassword, "Hashed password cannot be null.");

        return hash(rawPassword).equals(hashedPassword);
    }
}