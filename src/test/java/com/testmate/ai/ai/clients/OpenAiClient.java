package com.testmate.ai.ai.clients;

import com.testmate.ai.ai.models.AiRequest;
import com.testmate.ai.ai.models.AiResponse;

public class OpenAiClient implements AiClient {

    @Override
    public AiResponse generateResponse(AiRequest request) {
        throw new UnsupportedOperationException("OpenAI provider is not implemented yet. Configure it before live execution.");
    }
}
