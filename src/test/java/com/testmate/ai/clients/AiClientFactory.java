package com.testmate.ai.clients;

import com.testmate.ai.config.FrameworkConfig;

public final class AiClientFactory {

    private AiClientFactory() {
    }

    public static AiClient createClient() {
        String provider = FrameworkConfig.getAiProvider();
        if (provider == null || provider.equalsIgnoreCase("mock")) {
            return new MockAiClient();
        }
        throw new IllegalArgumentException("AI provider is not configured: " + provider);
    }
}
