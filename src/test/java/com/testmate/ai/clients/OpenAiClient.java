package com.testmate.ai.clients;

import com.testmate.ai.models.AiRequest;
import com.testmate.ai.models.AiResponse;

public class OpenAiClient implements AiClient {

    @Override
    public AiResponse generateResponse(AiRequest request) {
        throw new UnsupportedOperationException("OpenAI provider skeleton is available. Add endpoint, key, and request mapping before live execution.");
    }
}
