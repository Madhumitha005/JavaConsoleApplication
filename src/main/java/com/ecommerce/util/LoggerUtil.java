package com.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoggerUtil {

    private static LoggerUtil instance;

    private static final DateTimeFormatter FORMATTER =DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private LoggerUtil() {
    }

    public static LoggerUtil getInstance() {

        if (instance == null) {
            instance = new LoggerUtil();
        }
        return instance;
    }

    private void log(String type, String message) {

        String time = LocalDateTime.now().format(FORMATTER);
        System.out.println(
                "[" + time + "] "
                + "[" + type + "] "
                + message
        );
    }

    public void info(String message) {
        log("INFO", message);
    }

    public void warning(String message) {
        log("WARNING", message);
    }

    public void error(String message) {
        log("ERROR", message);
    }
}