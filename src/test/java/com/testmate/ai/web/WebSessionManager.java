package com.testmate.ai.web;

public final class WebSessionManager {

    private static final ThreadLocal<Object> PAGE = new ThreadLocal<>();

    private WebSessionManager() {
    }

    public static void setPage(Object page) {
        PAGE.set(page);
    }

    public static Object getPage() {
        return PAGE.get();
    }

    public static void clear() {
        PAGE.remove();
    }
}
