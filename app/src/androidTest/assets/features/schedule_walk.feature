Feature: Schedule or withdraw a proposed/scheduled walk

  Scenario: Schedule a proposed walk
    Given a main activity
    And the user's team has a scheduled walk
    And the user is the creator of the scheduled walk
    When the user clicks the scheduled walk button
    And the user clicks the schedule button
    Then the walk is scheduled
    And the user goes back to the home screen