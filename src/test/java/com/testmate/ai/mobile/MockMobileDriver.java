package com.testmate.ai.mobile;

public class MockMobileDriver {

    private boolean appOpened;
    private String currentScreen = "none";

    public void openApp() {
        this.appOpened = true;
        this.currentScreen = "home";
    }

    public boolean isAppOpened() {
        return appOpened;
    }

    public String currentScreen() {
        return currentScreen;
    }

    public void quit() {
        this.appOpened = false;
        this.currentScreen = "closed";
    }
}
