package com.testmate.ai.mobile;

import com.testmate.ai.core.config.ConfigReader;

public final class MobileSessionManager {

    private static final ThreadLocal<Object> DRIVER = new ThreadLocal<>();

    private MobileSessionManager() {
    }

    public static Object start() {
        if (DRIVER.get() != null) {
            return DRIVER.get();
        }

        String mode = ConfigReader.getOrDefault("mobile.execution.mode", "mock");
        Object driver;

        if ("mock".equalsIgnoreCase(mode)) {
            MockMobileDriver mockDriver = new MockMobileDriver();
            mockDriver.openApp();
            driver = mockDriver;
        } else if ("sauce".equalsIgnoreCase(mode)) {
            driver = SauceLabsMobileDriverFactory.createDriver();
        } else {
            driver = LocalMobileDriverFactory.createDriver();
        }

        DRIVER.set(driver);
        return driver;
    }

    public static Object getDriver() {
        return DRIVER.get();
    }

    public static void close() {
        Object driver = DRIVER.get();
        if (driver instanceof MockMobileDriver mockDriver) {
            mockDriver.quit();
        }
        DRIVER.remove();
    }

    public static void clear() {
        close();
    }
}
