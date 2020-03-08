Feature: Walk data population for team routes

  Scenario: No walk data for team route
    Given a routes activity
    And the user has team members
    And the user's team members have routes
    And the user's team members have previously walked their routes
    And the user has never walked a team route
    When the user clicks the team routes button
    And the user clicks a team route
    Then the user's team member's walk data is displayed
    And the user returns to the team routes screen
    And the user returns to the routes screen

  Scenario: User has walk data for team route
    Given a routes activity
    And the user has team members
    And the user's team members have routes
    And the user's team members have previously walked their routes
    And the user has previously walked the team routes
    When the user clicks the team routes button
    And the user clicks a team route
    Then the user's walk data is displayed
    And the user returns to the team routes screen
    And the user returns to the routes screen