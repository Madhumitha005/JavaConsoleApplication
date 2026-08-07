/*
 * StringUtil.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */
package com.ecommerce.common.util;

import org.springframework.stereotype.Component;

/**
 * Provides utility methods for string
 * manipulation and validation.
 */
@Component
public final class StringUtil {

    // Creates StringUtil object
    public StringUtil() {

    }

    // Removes leading and trailing spaces from the given string
    public String clean(final String value) {

        if (value == null) {

            return "";
        }

        return value.trim();
    }

    // Removes spaces and converts email address into lowercase
    public String cleanEmail(final String email) {

        if (email == null) {

            return "";
        }

        return email.trim().toLowerCase();
    }

    // Checks whether the given string is null or empty.
    public boolean isEmpty(final String value) {

        return value == null || value.trim().isEmpty();
    }
}