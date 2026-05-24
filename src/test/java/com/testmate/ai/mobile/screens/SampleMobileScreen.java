package com.testmate.ai.mobile.screens;

import com.testmate.ai.mobile.BaseScreen;
import com.testmate.ai.mobile.MockMobileDriver;

public class SampleMobileScreen extends BaseScreen {

    public void open() {
        driver();
    }

    public boolean isActive() {
        Object driver = driver();
        if (driver instanceof MockMobileDriver mockMobileDriver) {
            return mockMobileDriver.isAppOpened();
        }
        return driver != null;
    }

    public String currentScreen() {
        Object driver = driver();
        if (driver instanceof MockMobileDriver mockMobileDriver) {
            return mockMobileDriver.currentScreen();
        }
        return "unknown";
    }
}
