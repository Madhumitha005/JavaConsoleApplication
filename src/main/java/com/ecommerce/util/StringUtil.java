package com.ecommerce.util;

public final class StringUtil {

    // Singleton pattern
    private static StringUtil instance;

    // To avoid direct object creation
    private StringUtil() {
    }

    // Return object
    public static StringUtil getInstance() {

        if (instance == null) {
            instance = new StringUtil();// instace null create new object
        }
        return instance;
    }

    // Trim String
    public String clean(String value) {

        if (value == null) {
            return "";
        }
        return value.trim();
    }

    // Trim and Lowercase Email
    public String cleanEmail(String email) {

        if (email == null) {
            return "";
        }
        return email.trim().toLowerCase();
    }

    // Check Empty
    public boolean isEmpty(String value) {

        return value == null || value.trim().isEmpty();
    }
}