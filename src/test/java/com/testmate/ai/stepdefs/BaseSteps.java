package com.testmate.ai.stepdefs;

import com.testmate.ai.clients.AiClient;
import com.testmate.ai.clients.AiClientFactory;
import com.testmate.ai.context.ScenarioContext;
import com.testmate.ai.validators.AiResponseValidator;

public abstract class BaseSteps {

    protected final ScenarioContext scenarioContext = ScenarioContext.current();
    protected final AiClient aiClient = AiClientFactory.createClient();
    protected final AiResponseValidator aiResponseValidator = new AiResponseValidator();
}
