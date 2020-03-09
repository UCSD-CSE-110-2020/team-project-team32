Feature: Mark/Unmark a route as a favorite

  Scenario: Mark route as a favorite
    Given the user has a route
    And the user's route is not a favorite
    And a routes activity
    When the user clicks the favorite button for a route
    Then the route is favorited

  Scenario: Unmark route as a favorite
    Given the user has a route
    And the user's route is a favorite
    And a routes activity
    When the user clicks the favorite button for a route
    Then the route is un-favorited