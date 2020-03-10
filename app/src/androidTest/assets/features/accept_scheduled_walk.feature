Feature: Accept/Decline scheduled walk

  Scenario: Accept walk
    Given a main activity
    And the user's team has a scheduled walk
    And the user is not the creator of the scheduled walk
    When the user clicks the scheduled walk button
    And the user clicks the scheduled walk accept button
    Then the user accepts the scheduled walk
    And the user goes back to the home screen