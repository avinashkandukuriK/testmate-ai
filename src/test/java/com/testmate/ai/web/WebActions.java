package com.testmate.ai.web;

public class WebActions {

    public Object currentPage() {
        return WebSessionManager.getPage();
    }
}
