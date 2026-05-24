package com.testmate.ai.mobile;

public abstract class BaseScreen {

    protected Object driver() {
        return MobileSessionManager.getDriver();
    }
}
