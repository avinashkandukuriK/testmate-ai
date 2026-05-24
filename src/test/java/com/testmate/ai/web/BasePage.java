package com.testmate.ai.web;

import com.microsoft.playwright.Page;

public abstract class BasePage {

    protected Page page() {
        Page page = WebSessionManager.getPage();
        if (page == null) {
            return WebSessionManager.start();
        }
        return page;
    }

    public String title() {
        return page().title();
    }

    public String url() {
        return page().url();
    }
}
