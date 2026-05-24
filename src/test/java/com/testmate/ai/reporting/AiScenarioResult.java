package com.testmate.ai.reporting;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AiScenarioResult {
    private String scenarioName;
    private String status;
    private String provider;
    private String model;
    private String prompt;
    private String response;
    private long responseTimeMs;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    @Builder.Default
    private List<String> validations = new ArrayList<>();
    private String failureMessage;
}
