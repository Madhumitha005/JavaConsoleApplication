package com.ecommerce.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public final class PropertyUtil {

    private static PropertyUtil instance;

    private final Properties properties;

    private PropertyUtil() {

        this.properties = new Properties();

        try (final InputStream input =
                     getClass().getClassLoader()
                             .getResourceAsStream("application.properties")) {

            Objects.requireNonNull(input, "application.properties file not found.");

            this.properties.load(input);

        } catch (final IOException e) {

            throw new RuntimeException("Unable to load application.properties.", e);
        }
    }

    public static PropertyUtil getInstance() {

        if (instance == null) {
            instance = new PropertyUtil();
        }

        return instance;
    }
}