package com.testmate.ai.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiResponse {
    private String provider;
    private String model;
    private String prompt;
    private String content;
    private long responseTimeMs;
    private int statusCode;
}
