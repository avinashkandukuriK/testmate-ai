package com.testmate.ai.mobile;

public final class SauceLabsMobileDriverFactory {

    private SauceLabsMobileDriverFactory() {
    }

    public static Object createDriver() {
        throw new UnsupportedOperationException("Sauce Labs mobile execution requires sauce.username, sauce.access.key, sauce.app.url, platform, device, and version capabilities.");
    }
}
