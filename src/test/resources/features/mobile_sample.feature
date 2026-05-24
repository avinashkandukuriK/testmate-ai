@mobile
Feature: Mobile Sample Validation

  Scenario: Validate mobile sample starts successfully
    Given the mobile sample is configured
    When I start the mobile sample
    Then the mobile sample should be active
    And the mobile screen should be "home"
