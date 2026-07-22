package com.ecommerce.util;

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

    public String hash(String password) {
        return String.valueOf(password.hashCode());
    }
}