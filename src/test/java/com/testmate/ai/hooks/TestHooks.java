package com.testmate.ai.hooks;

import com.testmate.ai.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.time.LocalDateTime;

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
        ScenarioContext.current().set("scenarioEndTime", LocalDateTime.now());
        System.out.println("Completed scenario: " + scenario.getName() + " | Status: " + scenario.getStatus());
        ScenarioContext.remove();
    }
}
