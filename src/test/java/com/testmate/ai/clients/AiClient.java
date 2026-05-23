package com.testmate.ai.clients;

import com.testmate.ai.models.AiRequest;
import com.testmate.ai.models.AiResponse;

public interface AiClient {
    AiResponse generateResponse(AiRequest request);
}
