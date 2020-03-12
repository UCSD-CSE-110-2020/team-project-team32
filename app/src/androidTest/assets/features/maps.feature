Feature: Launch Google Maps from clicking the starting point of a scheduled walk

  Scenario: Launch maps
    Given a main activity
    And the user's team has a scheduled walk
    And the scheduled walk has a starting point
    When the user clicks the scheduled walk button
    And the user clicks the starting point
    Then Google Maps is launched with the starting point as the search query
    And the user returns to the previous screen from the proposed walk screen