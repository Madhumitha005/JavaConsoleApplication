package com.ecommerce.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertyUtil {

    private static PropertyUtil instance;
    private final Properties properties;

    private PropertyUtil() {

        // Create empty object initially
        properties = new Properties();

        try {

            InputStream input = getClass()
                    .getClassLoader()
                    .getResourceAsStream("application.properties");

            if (input == null) {
                throw new RuntimeException("Properties file not found.");
            }
            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException("Unable to load properties file.");
        }
    }

    public static PropertyUtil getInstance() {

        if (instance == null) {
            instance = new PropertyUtil();
        }
        return instance;
    }
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}