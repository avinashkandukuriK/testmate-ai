package com.testmate.ai.ai.clients;

import com.testmate.ai.ai.models.AiRequest;
import com.testmate.ai.ai.models.AiResponse;

public class MockAiClient implements AiClient {

    @Override
    public AiResponse generateResponse(AiRequest request) {
        long start = System.currentTimeMillis();
        String prompt = request.getPrompt() == null ? "" : request.getPrompt().toLowerCase();
        String response;

        if (prompt.contains("restricted")) {
            response = "I cannot help with restricted instructions. I can help with safe best practices and defensive guidance.";
        } else if (prompt.contains("refund")) {
            response = "Our refund policy allows eligible customers to request a refund within the approved return window. Please review the official policy page or contact support for account-specific details.";
        } else {
            response = "Here is a professional and relevant response based on the user request. Please verify business-specific details with the approved source of truth.";
        }

        return AiResponse.builder()
                .provider("mock")
                .model("mock-enterprise-ai")
                .prompt(request.getPrompt())
                .content(response)
                .responseTimeMs(System.currentTimeMillis() - start)
                .statusCode(200)
                .build();
    }
}
