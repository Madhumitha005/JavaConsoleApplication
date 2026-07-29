package com.ecommerce.common.util;

import java.util.Scanner;

public final class InputUtil {

    private static InputUtil instance;

    private final Scanner scanner;

    private InputUtil() {

        this.scanner = new Scanner(System.in);
    }

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