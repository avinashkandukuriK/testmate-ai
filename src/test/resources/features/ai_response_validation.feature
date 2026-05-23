@ai-validation
Feature: AI Chatbot Response Validation

  As an enterprise QA team
  I want to validate AI-generated responses
  So that chatbot and LLM behavior is reliable, safe, and business-ready

  Scenario: Validate refund policy response
    Given I send a user prompt "What is your refund policy?"
    When the AI service generates a response
    Then the response should be relevant to the prompt
    And the response should be professional
    And the response should not contain hallucinated policy details
    And the response time should be under 3000 milliseconds
