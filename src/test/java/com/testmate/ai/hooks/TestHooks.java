package com.testmate.ai.hooks;

import com.testmate.ai.context.ScenarioContext;
import com.testmate.ai.models.AiRequest;
import com.testmate.ai.models.AiResponse;
import com.testmate.ai.reporting.AiReportManager;
import com.testmate.ai.reporting.AiScenarioResult;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class TestHooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        ScenarioContext.current().clear();
        ScenarioContext.current().set("scenarioName", scenario.getName());
        ScenarioContext.current().set("scenarioStartTime", LocalDateTime.now());
        System.out.println("Starting scenario: " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        ScenarioContext context = ScenarioContext.current();
        LocalDateTime startedAt = context.get("scenarioStartTime", LocalDateTime.class);
        AiRequest request = context.get("request", AiRequest.class);
        AiResponse response = context.get("response", AiResponse.class);
        List<String> validations = context.get("validations", List.class);

        AiScenarioResult result = AiScenarioResult.builder()
                .scenarioName(scenario.getName())
                .status(scenario.getStatus().name())
                .provider(response == null ? "N/A" : response.getProvider())
                .model(response == null ? "N/A" : response.getModel())
                .prompt(request == null ? "N/A" : request.getPrompt())
                .response(response == null ? "N/A" : response.getContent())
                .responseTimeMs(response == null ? 0 : response.getResponseTimeMs())
                .startedAt(startedAt)
                .finishedAt(LocalDateTime.now())
                .validations(validations == null ? Collections.emptyList() : validations)
                .failureMessage(scenario.isFailed() ? "Review Cucumber report for scenario details." : null)
                .build();

        AiReportManager.addResult(result);
        System.out.println("Completed scenario: " + scenario.getName() + " | Status: " + scenario.getStatus());
        ScenarioContext.remove();
    }

    @AfterAll
    public static void afterAllScenarios() {
        AiReportManager.flushReports();
    }
}
