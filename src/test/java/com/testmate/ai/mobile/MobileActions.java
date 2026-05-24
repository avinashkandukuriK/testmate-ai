package com.testmate.ai.mobile;

public class MobileActions {

    public Object currentDriver() {
        Object driver = MobileSessionManager.getDriver();
        return driver == null ? MobileSessionManager.start() : driver;
    }

    public void openApplication() {
        currentDriver();
    }

    public boolean isApplicationOpen() {
        Object driver = currentDriver();
        if (driver instanceof MockMobileDriver mockDriver) {
            return mockDriver.isAppOpened();
        }
        return driver != null;
    }

    public String currentScreen() {
        Object driver = currentDriver();
        if (driver instanceof MockMobileDriver mockDriver) {
            return mockDriver.currentScreen();
        }
        return "unknown";
    }
}
