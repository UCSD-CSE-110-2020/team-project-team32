package com.example.cse110_project.test.bdd_tests;

import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.TeamActivity;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.database.FirebaseFirestoreAdapter;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.Team;
import com.example.cse110_project.user_routes.TeamRoute;
import com.example.cse110_project.user_routes.UserRoute;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.notNullValue;

public class SendInviteBDDTests {
    private Map<String, Object> invite;
    private String invitedMember;
    private ActivityTestRule<TeamActivity> teamActivityTestRule = new ActivityTestRule<>(TeamActivity
            .class);

    private Map<String, String> nameIdMap = new HashMap<>();

    @Before
    public void setup() {
        Intents.init();
        WWRApplication.setDatabase(new TestDatabaseService());
        WWRApplication.getUser().getTeam().setId("Test id");
    }

    @After
    public void tearDown() {
        teamActivityTestRule.getActivity().finish();
        Intents.release();
    }

    @Given("a team activity")
    public void aTeamActivity() {
        System.out.println("STARTING TEAM_ACTIVITY");
        teamActivityTestRule.launchActivity(null);
        assertThat(teamActivityTestRule.getActivity(), notNullValue());
    }

    public static int getLayoutIdFromString(String resName) {
        try {
            Field idField = R.id.class.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @When("the user clicks the new team member button")
    public void theUserClicksTheNewTeamMemberButton() {
        onView(withId(R.id.teamAddMember))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @And("the user enters {string} in the name text field")
    public void theUserEntersInTheNameTextField(String input) {
        onView(withId(R.id.inviteNameInput))
                .check(matches(isDisplayed()))
                .perform(typeText(input));
    }

    @And("the user enters {string} in the email text field")
    public void theUserEntersInTheEmailTextField(String input) {
        onView(withId(R.id.inviteEmailInput))
                .check(matches(isDisplayed()))
                .perform(typeText(input));
    }

    @And("the user clicks the send button")
    public void theUserClicksTheSendButton() {
        onView(withId(R.id.sendInviteButton))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Then("(.*) receives an invite to the user's team")
    public void inviteeReceivesInvite(String memberId) {
        assertEquals(invite.get(Team.TEAM_ID_KEY), WWRApplication.getUser().getTeam().getId());
        assertEquals(invite.get(Team.INVITER_KEY), WWRApplication.getUser().getEmail());
        assertEquals(invitedMember, memberId);
    }

    @Then("an error message is displayed")
    public void anErrorMessageIsDisplayed() {
        //onView(withText(teamActivityTestRule.getActivity().getResources().getString(R.string.InvalidInvite))).check(matches(isDisplayed()));
        assertNull(invite);
        assertNull(invitedMember);
    }


    private class TestDatabaseService implements DatabaseService {
        @Override
        public void addRoute(UserRoute route) { }

        @Override
        public void updateRoute(UserRoute route) { }

        @Override
        public void removeInvite(String teamId, String memberId) { }

        @Override
        public void createInvite(String teamId, String memberId, Map<String, Object> content) {
            invite = content;
            invitedMember = memberId;
        }

        @Override
        public List<Map<String, Object>> getInvites(String memberId) {
            return null;
        }

        @Override
        public void createTeam(Team team) { }

        @Override
        public void updateTeam(Team team) {

        }

        @Override
        public void getRoutesByUser(String userId, List<TeamRoute> routes) {

        }

        @Override
        public void getRoutes(List<Route> routes) {

        }

        @Override
        public void getTeamMembers(Team team) {

        }
    }

}