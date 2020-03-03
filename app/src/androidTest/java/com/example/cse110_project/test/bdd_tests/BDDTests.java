package com.example.cse110_project.test.bdd_tests;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.TeamActivity;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.UserRoute;
<<<<<<< HEAD
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
=======
>>>>>>> feature/accept-invite

import org.hamcrest.Description;

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
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;

public class BDDTests {
    private Invite invite;
    private Team team;

    private ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private ActivityTestRule<TeamActivity> teamActivityTestRule = new ActivityTestRule<>(TeamActivity
            .class);

    private Map<String, String> nameIdMap = new HashMap<>();

    @Before
    public void setup() {
        Intents.init();
        WWRApplication.setDatabase(new TestDatabaseService());
        WWRApplication.getUser().setEmail("wwruser@gmail.com");
        WWRApplication.getUser().setHeight(61);
        team = WWRApplication.getUser().getTeam();
        team.setId("ViewTeamTest");
        team.getMembers().clear();
    }

    @After
    public void tearDown() {
        if (mainActivityTestRule.getActivity() != null) {
            mainActivityTestRule.getActivity().finish();
        }
        if (teamActivityTestRule.getActivity() != null) {
            teamActivityTestRule.getActivity().finish();
        }
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
        assertEquals(invite.getTeamId(), WWRApplication.getUser().getTeam().getId());
        assertEquals(invite.getCreatorId(), WWRApplication.getUser().getEmail());
        assertEquals(invite.getInvitedMemberId(), memberId);
    }

    @Then("an error message is displayed")
    public void anErrorMessageIsDisplayed() {
        //onView(withText(teamActivityTestRule.getActivity().getResources().getString(R.string.InvalidInvite))).check(matches(isDisplayed()));
        assertNull(invite);
    }

    @Given("a main activity")
    public void aMainActivity() {
        System.out.println("STARTING MAIN_ACTIVITY");
        mainActivityTestRule.launchActivity(null);
        assertThat(mainActivityTestRule.getActivity(), notNullValue());
    }

    @Given("the user has no team members")
    public void theUserHasNoTeamMembers() {}

    @Given("the user has team members")
    public void theUserHasTeamMembers() {
        team.getMembers().add(new TeamMember("Team 32", "email0", Color.RED));
        team.getMembers().get(0).setStatus(TeamMember.STATUS_MEMBER);
        team.getMembers().add(new TeamMember("CSE 110", "email1", Color.GREEN));
        team.getMembers().get(0).setStatus(TeamMember.STATUS_MEMBER);
        team.getMembers().add(new TeamMember("Project Team", "email2", Color.BLUE));
    }

    @Given("the user has a pending team member")
    public void theUserHasAPendingTeamMember() {
        team.getMembers().add(new TeamMember("Pending Member", "email3", Color.YELLOW));
    }

    @When("the user clicks the team button")
    public void theUserClicksTeamButton() {
        onView(withId(R.id.teamButton))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Then("team members are displayed")
    public void teamMembersAreDisplayed() {
        for (TeamMember member : team.getMembers()) {
            onView(allOf(withId(R.id.teamRowName), withText(member.getName())))
                    .check(matches(isDisplayed()));

            String[] initialsArr = member.getName().split(" ");
            StringBuilder initials = new StringBuilder();
            for (String initial : initialsArr) {
                if (initial.length() > 0) {
                    initials.append(initial.charAt(0));
                }
            }

            ViewInteraction initialView =
                    onView(allOf(withId(R.id.teamRowInitials), withText(initials.toString())));
            initialView.check(matches(isDisplayed()));
            initialView.check(matches(new BackgroundColorMatcher(member.getColor())));
        }
    }

    @Then("a pending team member is displayed")
    public void aPendingTeamMemberIsDisplayed() {
        for (TeamMember member : team.getMembers()) {
            if (member.getStatus() == TeamMember.STATUS_PENDING) {
                ViewInteraction nameView =
                        onView(allOf(withId(R.id.teamRowName), withText(member.getName())));
                nameView.check(matches(isDisplayed()));
                nameView.check(matches(new PendingTeamMemberNameMatcher()));
            }
        }
    }

    @Then("no team members are displayed")
    public void noTeamMembersAreDisplayed() {
        onView(withId(R.id.teamRowName)).check(doesNotExist());
        onView(withId(R.id.teamRowInitials)).check(doesNotExist());
    }

    @Then("the user returns to the home screen")
    public void theUserReturnsToTheHomeScreen() {
        onView(withId(R.id.teamHomeButton)).perform(click());
    }

    private class PendingTeamMemberNameMatcher extends BoundedMatcher<View, TextView> {
        public PendingTeamMemberNameMatcher() {
            super(TextView.class);
        }

        @Override
        protected boolean matchesSafely(TextView item) {
            return item.getExplicitStyle() == R.style.pendingText;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Style expected to be " + R.style.pendingText);
        }
    }

    private class BackgroundColorMatcher extends BoundedMatcher<View, TextView> {
        private final int color;

        public BackgroundColorMatcher(int color) {
            super(TextView.class);
            this.color = color;
        }

        @Override
        protected boolean matchesSafely(TextView item) {
            if ( ! (item.getBackground() instanceof ColorDrawable)) {
                return false;
            } else {
                return ((ColorDrawable)item.getBackground()).getColor() == color;
            }
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("Icon does not match expected member");
        }
    }


    private class TestDatabaseService implements DatabaseService {
        @Override
        public void addRoute(UserRoute route) { }

        @Override
        public void updateRoute(UserRoute route) { }

        @Override
        public void getInvites(String memberId, List<Invite> invites) {

        }

        @Override
        public void addInvite(Invite inv) {
            invite = inv;
        }

        @Override
        public Task<?> createTeam(Team team) { return null; }

        @Override
        public void removeTeam(Team team) {

        }

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
<<<<<<< HEAD
        public Task<?> getTeamMembers(Team team) {
            return null;
=======
        public void removeInvite(Invite invite) {

        }

        @Override
        public void getTeamMembers(Team team) {

>>>>>>> feature/accept-invite
        }
    }

}