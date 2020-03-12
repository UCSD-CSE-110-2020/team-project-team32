Feature: Notify on changes to scheduled walk responses

  Scenario: Teammate accepts a walk
    Given a main activity
    And the user's team has a scheduled walk
    And the user is the creator of the scheduled walk
    And the user has a team member
    And the user's team member has not responded to the scheduled walk
    When the user's team member accepts the scheduled walk
    Then the user receives a notification

  Scenario: Teammate schedules a walk
    Given a main activity
    And the user has a team member
    And the user's team has a scheduled walk
    And the user's team member is the creator of the scheduled walk
    And the team walk has not yet been scheduled
    When the user's team member schedules the walk
    Then the user receives a notification