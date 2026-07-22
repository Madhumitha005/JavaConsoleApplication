package com.ecommerce.util;

import java.util.Scanner;

public final class InputUtil {

    private static InputUtil instance;
    private final Scanner scanner;

    private InputUtil() {
        scanner = new Scanner(System.in);
    }

    public static InputUtil getInstance() {

        if (instance == null) {
            instance = new InputUtil();
        }
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }
}