package com.testmate.ai.web;

public abstract class BasePage {

    protected Object page() {
        return WebSessionManager.getPage();
    }
}
