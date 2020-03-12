Feature: Propose a scheduled walk

  Scenario: No scheduled walk exists
    Given the user has a route
    And the user's team does not have a scheduled or proposed walk
    And a routes activity
    When the user clicks the route
    And the user clicks the propose walk button
    And the user fills in the time and date of the proposed walk
    Then the walk is proposed
    And the proposed walk details are displayed
    And the user returns to the previous screen from the proposed walk screen
    And the user returns to the routes screen from the details screen

  Scenario: Scheduled walk exists
    Given the user has a route
    And the user's team has a scheduled walk
    And a routes activity
    When the user clicks the route
    And the user clicks the propose walk button
    Then an error message is displayed for proposed walk
    And the user returns to the routes screen from the details screen