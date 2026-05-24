@web @parabank
Feature: ParaBank Web Smoke Validation

  Background:
    Given ParaBank is configured as the web test application

  Scenario: Validate ParaBank landing page is available
    When I open the ParaBank home page
    Then the ParaBank customer login panel should be visible
    And the ParaBank public service categories should be visible

  Scenario: Validate ParaBank services page is available
    When I open the ParaBank services page
    Then the ParaBank page title should contain "Services"
    And the ParaBank services page should list banking service endpoints

  Scenario: Validate ParaBank registration form is ready for customer enrollment
    When I open the ParaBank registration page
    Then the ParaBank page title should contain "Register"
    And the ParaBank registration form should contain required customer fields

  Scenario: Validate ParaBank customer lookup form is ready for account recovery
    When I open the ParaBank customer lookup page
    Then the ParaBank page title should contain "Customer Lookup"
    And the ParaBank customer lookup form should contain identity verification fields
