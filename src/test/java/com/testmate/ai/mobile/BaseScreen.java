package com.testmate.ai.mobile;

public abstract class BaseScreen {

    protected Object driver() {
        Object driver = MobileSessionManager.getDriver();
        return driver == null ? MobileSessionManager.start() : driver;
    }
}
