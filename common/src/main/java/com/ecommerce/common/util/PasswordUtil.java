/*
 * PasswordUtil.java
 *
 * Version 1.0
 *
 * July 30, 2026
 *
 * Copyright (c) 2026.
 * All Rights Reserved.
 */

package com.ecommerce.common.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public final class PasswordUtil {

    public String encrypt(final String password) {

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );
    }

    public boolean matches(
            final String rawPassword,
            final String hashedPassword) {

        return BCrypt.checkpw(rawPassword, hashedPassword);
    }
}