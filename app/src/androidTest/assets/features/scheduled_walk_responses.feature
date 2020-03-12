Feature: View teammates' responses to a scheduled or proposed walk

  Scenario: Teammates accepted the walk
    Given a main activity
    And the user has team members
    And the user's team has a scheduled walk
    And the user's team members have accepted the walk
    When the user clicks the scheduled walk button
    Then the user's team members' statuses are displayed as accepted
    And the user returns to the previous screen from the proposed walk screen

  Scenario: Teammates have not responded to the walk
    Given a main activity
    And the user has team members
    And the user's team has a scheduled walk
    And the user's team members have not responded to the walk
    When the user clicks the scheduled walk button
    Then the user's team members' statuses are displayed as "no response"
    And the user returns to the previous screen from the proposed walk screen