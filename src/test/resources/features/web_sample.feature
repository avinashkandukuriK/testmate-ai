@web
Feature: Web UI Sample Validation

  Scenario: Validate configured web page opens successfully
    Given the web test application is configured
    When I open the configured web page
    Then the web page title should not be empty
