package com.testmate.ai.mobile;

public final class LocalMobileDriverFactory {

    private LocalMobileDriverFactory() {
    }

    public static Object createDriver() {
        throw new UnsupportedOperationException("Local Appium execution requires Appium server URL, device name, app path/package, and platform capabilities.");
    }
}
