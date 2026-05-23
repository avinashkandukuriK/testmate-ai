package com.testmate.ai.config;

public final class FrameworkConfig {

    private FrameworkConfig() {
    }

    public static String getAiProvider() {
        return System.getProperty("ai.provider", "mock");
    }

    public static long getDefaultResponseTimeLimitMs() {
        return Long.parseLong(System.getProperty("ai.response.limit.ms", "3000"));
    }
}
