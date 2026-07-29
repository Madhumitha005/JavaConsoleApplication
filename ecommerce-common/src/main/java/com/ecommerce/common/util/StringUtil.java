package com.ecommerce.common.util;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public final class StringUtil {

    private static StringUtil instance;

    private StringUtil() {
    }

    public static StringUtil getInstance() {

        if (instance == null) {
            instance = new StringUtil();
        }

        return instance;
    }

    // Trim String
    public String clean(final String value) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }

    // Trim and Lowercase Email
    public String cleanEmail(final String email) {

        if (email == null) {
            return "";
        }

        return email.trim().toLowerCase();
    }

    // Check Empty
    public boolean isEmpty(final String value) {

        return value == null || value.trim().isEmpty();
    }
}