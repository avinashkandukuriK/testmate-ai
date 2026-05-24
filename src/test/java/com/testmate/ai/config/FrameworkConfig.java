package com.testmate.ai.config;

public final class FrameworkConfig {

    private FrameworkConfig() {
    }

    public static String getAiProvider() {
        return ConfigReader.getOrDefault("ai.provider", "mock");
    }

    public static long getDefaultResponseTimeLimitMs() {
        return ConfigReader.getLongOrDefault("ai.response.limit.ms", 3000);
    }

    public static String getDefaultModel() {
        return ConfigReader.getOrDefault("ai.default.model", "mock-enterprise-ai");
    }

    public static String getReportOutputDir() {
        return ConfigReader.getOrDefault("ai.report.output.dir", "target/testmate-ai-reports");
    }
}
