Feature: View team members

 Scenario: No team members exist
   Given a main activity
   And the user has no team members
   When the user clicks the team button
   Then no team members are displayed
   And the user returns to the home screen

 Scenario: Has team members
   Given a main activity
   And the user has team members
   When the user clicks the team button
   Then team members are displayed
   And the user returns to the home screen

 Scenario: Has pending team member
   Given a main activity
   And the user has team members
   And the user has a pending team member
   When the user clicks the team button
   Then team members are displayed
   And the user returns to the home screen


 @skipAndroid
 Scenario: we gonna skip this one for android
   Given whatever setup
   When whatever event
   Then whatever assert