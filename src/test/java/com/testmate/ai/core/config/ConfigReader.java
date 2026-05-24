package com.testmate.ai.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {
        load("config/application.properties");
    }

    private ConfigReader() {
    }

    private static void load(String fileName) {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load configuration file: " + fileName, e);
        }
    }

    public static String get(String key) {
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.isBlank()) {
            return systemValue;
        }
        String envKey = key.toUpperCase().replace('.', '_');
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return PROPERTIES.getProperty(key);
    }

    public static String getOrDefault(String key, String defaultValue) {
        String value = get(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    public static long getLongOrDefault(String key, long defaultValue) {
        String value = get(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Long.parseLong(value);
    }
}
