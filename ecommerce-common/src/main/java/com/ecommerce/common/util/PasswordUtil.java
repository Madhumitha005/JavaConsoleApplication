package com.ecommerce.common.util;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class PasswordUtil {

    private static PasswordUtil instance;

    private PasswordUtil() {
    }

    public static PasswordUtil getInstance() {

        if (instance == null) {
            instance = new PasswordUtil();
        }

        return instance;
    }

    public String hash(final String password) {

        Objects.requireNonNull(password, "Password cannot be null.");

        return String.valueOf(password.hashCode());
    }

    public boolean matches(final String rawPassword, final String hashedPassword) {

        Objects.requireNonNull(rawPassword, "Password cannot be null.");
        Objects.requireNonNull(hashedPassword, "Hashed password cannot be null.");

        return hash(rawPassword).equals(hashedPassword);
    }
}