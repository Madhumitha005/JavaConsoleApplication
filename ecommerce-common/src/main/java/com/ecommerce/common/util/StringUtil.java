/*
 * StringUtil.java
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
 * Provides utility methods for string
 * manipulation and validation.
 *
 * This class follows the Singleton design
 * pattern to ensure that only one instance
 * exists throughout the application.
 *
 */
@Component
public final class StringUtil {

    private static StringUtil instance;

    private StringUtil() {
    }

    /**
     * Returns the singleton instance.
     *
     * @return StringUtil instance
     */
    public static StringUtil getInstance() {

        if (instance == null) {
            instance = new StringUtil();
        }

        return instance;
    }

    /**
     * Removes leading and trailing spaces
     * from the given string.
     *
     * @param value input string
     * @return trimmed string or an empty
     *         string if the input is null
     */
    public String clean(final String value) {

        if (value == null) {
            return "";
        }

        return value.trim();
    }

    /**
     * Removes leading and trailing spaces
     * and converts the email address to
     * lowercase.
     *
     * @param email email address
     * @return cleaned email or an empty
     *         string if the input is null
     */
    public String cleanEmail(final String email) {

        if (email == null) {
            return "";
        }

        return email.trim().toLowerCase();
    }

    /**
     * Checks whether the given string is
     * null or empty after trimming.
     *
     * @param value input string
     * @return true if the string is empty,
     *         otherwise false
     */
    public boolean isEmpty(final String value) {

        return value == null || value.trim().isEmpty();
    }
}