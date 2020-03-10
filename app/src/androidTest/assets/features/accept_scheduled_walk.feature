Feature: Accept/Decline scheduled walk

  Scenario: Accept walk
    Given a main activity
    And the user's team has a scheduled walk
    And the user is not the creator of the scheduled walk
    When the user clicks the scheduled walk button
    And the user clicks the scheduled walk accept button
    Then the user accepts the scheduled walk
    And the user goes back to the home screen

  Scenario: Decline walk (bad time)
    Given a main activity
    And the user's team has a scheduled walk
    And the user is not the creator of the scheduled walk
    When the user clicks the scheduled walk button
    And the user clicks the scheduled walk decline (bad time) button
    Then the user declines the scheduled walk due to a bad time
    And the user goes back to the home screen

  Scenario: Decline walk (bad route)
    Given a main activity
    And the user's team has a scheduled walk
    And the user is not the creator of the scheduled walk
    When the user clicks the scheduled walk button
    And the user clicks the scheduled walk decline (bad route) button
    Then the user declines the scheduled walk due to a bad route
    And the user goes back to the home screen