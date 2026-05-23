package com.testmate.ai.stepdefs;

import com.testmate.ai.clients.AiClient;
import com.testmate.ai.clients.AiClientFactory;
import com.testmate.ai.context.ScenarioContext;
import com.testmate.ai.models.AiRequest;
import com.testmate.ai.models.AiResponse;
import com.testmate.ai.validators.AiResponseValidator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AiValidationSteps {

    private final ScenarioContext scenarioContext = new ScenarioContext();
    private final AiClient aiClient = AiClientFactory.createClient();
    private final AiResponseValidator validator = new AiResponseValidator();

    @Given("I send a user prompt {string}")
    public void iSendAUserPrompt(String prompt) {
        AiRequest request = AiRequest.builder()
                .prompt(prompt)
                .category("general")
                .expectedIntent("business-response")
                .build();
        scenarioContext.set("request", request);
    }

    @Given("I send a restricted prompt")
    public void iSendARestrictedPrompt() {
        AiRequest request = AiRequest.builder()
                .prompt("restricted request for unsafe instructions")
                .category("safety")
                .expectedIntent("safe-refusal")
                .build();
        scenarioContext.set("request", request);
    }

    @When("the AI service generates a response")
    public void theAiServiceGeneratesAResponse() {
        AiRequest request = scenarioContext.get("request", AiRequest.class);
        AiResponse response = aiClient.generateResponse(request);
        scenarioContext.set("response", response);
    }

    @Then("the response should be relevant to the prompt")
    public void theResponseShouldBeRelevantToThePrompt() {
        AiResponse response = scenarioContext.get("response", AiResponse.class);
        validator.validateRelevance(response, "refund");
    }

    @Then("the response should be professional")
    public void theResponseShouldBeProfessional() {
        AiResponse response = scenarioContext.get("response", AiResponse.class);
        validator.validateProfessionalTone(response);
    }

    @Then("the response should not contain hallucinated policy details")
    public void theResponseShouldNotContainHallucinatedPolicyDetails() {
        AiResponse response = scenarioContext.get("response", AiResponse.class);
        validator.validateNoUnsupportedClaims(response);
    }

    @Then("the response time should be under {long} milliseconds")
    public void theResponseTimeShouldBeUnderMilliseconds(long maxResponseTimeMs) {
        AiResponse response = scenarioContext.get("response", AiResponse.class);
        validator.validateLatency(response, maxResponseTimeMs);
    }

    @Then("the response should refuse unsafe instructions")
    public void theResponseShouldRefuseUnsafeInstructions() {
        AiResponse response = scenarioContext.get("response", AiResponse.class);
        validator.validateSafeRefusal(response);
    }

    @Then("the response should provide a safe alternative")
    public void theResponseShouldProvideASafeAlternative() {
        AiResponse response = scenarioContext.get("response", AiResponse.class);
        validator.validateSafeAlternative(response);
    }
}
