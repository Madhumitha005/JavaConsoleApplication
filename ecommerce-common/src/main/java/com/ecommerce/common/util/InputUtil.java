/*
 * InputUtil.java
 *
 * Version 1.0
 *
 * July 24, 2026
 *
 * Copyright (c) 2026. All Rights Reserved.
 */
package com.ecommerce.common.util;

import java.util.Scanner;

/**
 * Provides a single Scanner instance for
 * reading user input throughout the
 * application.
 *
 * This class follows the Singleton design
 * pattern to ensure that only one Scanner
 * object is created.
 *
 */
public final class InputUtil {

    private static InputUtil instance;

    private final Scanner scanner;

    /**
     * Creates an InputUtil instance.
     */
    private InputUtil() {

        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the singleton instance.
     *
     * @return InputUtil instance
     */
    public static InputUtil getInstance() {

        if (instance == null) {
            instance = new InputUtil();
        }

        return instance;
    }

    public Scanner getScanner() {

        return this.scanner;
    }
}