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
        if (provider.equalsIgnoreCase("openai")) {
            return new OpenAiClient();
        }
        if (provider.equalsIgnoreCase("gemini")) {
            return new GeminiClient();
        }
        throw new IllegalArgumentException("Unsupported AI provider: " + provider);
    }
}
