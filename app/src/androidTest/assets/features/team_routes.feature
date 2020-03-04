Feature: View list of teammate routes

  Scenario: User has no team members
    Given the user has routes
    And a routes activity
    And the user has no team members
    When the user clicks the team routes button
    Then no team routes are displayed
    And the user returns to the routes screen

  Scenario: User has team members
    Given a routes activity
    And the user has team members
    And the user's team members have routes
    When the user clicks the team routes button
    Then team routes are displayed
    And the user returns to the routes screen