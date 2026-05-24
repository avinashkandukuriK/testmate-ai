package com.testmate.ai.ai.clients;

import com.testmate.ai.ai.models.AiRequest;
import com.testmate.ai.ai.models.AiResponse;

public interface AiClient {
    AiResponse generateResponse(AiRequest request);
}
