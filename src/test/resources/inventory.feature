Feature: Inventory Lookup Validation

  This feature validates inventory data returned from the API
  against the records stored in the database (DB2 or SQL).

  @smoke @inventory
  Scenario Outline: Validate inventory item-club combination against database
    Given I request inventory for item "<itemNumber>" and club "<clubNumber>"
    Then the API response should match the expected values:
      | status | effectiveDate | outOfStockDate |

    Examples:
      | itemNumber | clubNumber | status | effectiveDate | outOfStockDate |
      | 12345      | TX01       | A      | 2024-04-01     | 2024-05-01     |
      | 67890      | TX02       | R      | 2024-05-15     | 2024-06-15     |
