Feature: Accept or decline a received team invite

  Scenario: No invite
    Given a main activity
    And the user has no invites
    Then the invite button is not displayed

  Scenario: Accept invite
    Given the user has an invite
    And a main activity
    When the user clicks the invite button
    And the user clicks the accept button
    Then the user is added to the team as a member
    And the user's invite is removed
    And the user's team becomes the inviting team

  Scenario: Decline invite
    Given the user has an invite
    And the user has no team members
    And a main activity
    When the user clicks the invite button
    And the user clicks the decline button
    And the user's invite is removed
    And the user is removed from the inviting team
    And the user's team is empty