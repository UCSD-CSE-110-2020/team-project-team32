Feature: Display an icon for previously walked routes

  Scenario: Route was previously walked
    Given the user has a route
    And the user has previously walked the route
    And a routes activity
    Then a previously-walked icon is displayed on the route entry
    When the user clicks the route
    Then a previously-walked icon is displayed on the route details
    And the user returns to the routes screen from the details screen

  Scenario: Route was not previously walked
    Given the user has a route
    And the user has never walked the route
    And a routes activity
    Then a previously-walked icon is not displayed on the route entry
    When the user clicks the route
    Then a previously-walked icon is not displayed on the route details
    And the user returns to the routes screen from the details screen
