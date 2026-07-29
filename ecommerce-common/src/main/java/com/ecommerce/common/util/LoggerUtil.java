package com.ecommerce.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LoggerUtil {

    private static LoggerUtil instance;

    private final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private LoggerUtil() {
    }

    public static LoggerUtil getInstance() {

        if (instance == null) {
            instance = new LoggerUtil();
        }

        return instance;
    }

    private void log(final String type, final String message) {

        final String time =
                LocalDateTime.now().format(FORMATTER);

        System.out.println(
                "[" + time + "] "
                        + "[" + type + "] "
                        + message
        );
    }

    public void info(final String message) {

        this.log("INFO", message);
    }

    public void error(final String message) {

        this.log("ERROR", message);
    }
}