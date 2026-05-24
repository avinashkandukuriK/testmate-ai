package com.testmate.ai.web;

import com.microsoft.playwright.Page;

public class WebActions {

    public Page currentPage() {
        Page page = WebSessionManager.getPage();
        return page == null ? WebSessionManager.start() : page;
    }

    public void navigateTo(String url) {
        currentPage().navigate(url);
    }

    public String getTitle() {
        return currentPage().title();
    }

    public String getUrl() {
        return currentPage().url();
    }
}
