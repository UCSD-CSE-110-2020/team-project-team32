Feature: Send invite to new team member

  Scenario: Invite successfully sent
    Given a team activity
    When the user clicks the new team member button
    And the user enters "WWR User" in the name text field
    And the user enters "wwruser@gmail.com" in the email text field
    And the user clicks the send button
    Then wwruser@gmail.com receives an invite to the user's team

  Scenario: No data provided
    Given a team activity
    When the user clicks the new team member button
    And the user clicks the send button
    Then an error message is displayed

  @skipAndroid
  Scenario: we gonna skip this one for android
    Given whatever setup
    When whatever event
    Then whatever assert