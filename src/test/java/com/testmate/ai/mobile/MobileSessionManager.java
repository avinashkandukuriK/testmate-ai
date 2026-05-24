package com.testmate.ai.mobile;

public final class MobileSessionManager {

    private static final ThreadLocal<Object> DRIVER = new ThreadLocal<>();

    private MobileSessionManager() {
    }

    public static void setDriver(Object driver) {
        DRIVER.set(driver);
    }

    public static Object getDriver() {
        return DRIVER.get();
    }

    public static void clear() {
        DRIVER.remove();
    }
}
