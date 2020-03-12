package com.example.cse110_project.test.bdd_tests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.cse110_project.MainActivity;
import com.example.cse110_project.R;
import com.example.cse110_project.RouteDetailsActivity;
import com.example.cse110_project.RoutesActivity;
import com.example.cse110_project.TeamActivity;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.DatabaseService;
import com.example.cse110_project.database.RouteFirebaseAdapter;
import com.example.cse110_project.team.Invite;
import com.example.cse110_project.team.ScheduledWalk;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.team.Team;
import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.user_routes.RouteData;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.util.DataConstants;
import com.example.cse110_project.util.MapsMediator;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.ListenerRegistration;

import org.hamcrest.Description;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNotEquals;

public class BDDTests {
    private DatabaseService db;
    private User user;
    private Team team;

    private Invite invite;
    private Team invitedTeam;
    private TeamMember inviter;
    private int scheduledWalkUserStatus;
    private int scheduledWalkStatus;

    private boolean mapsLaunched;

    private ActivityTestRule<MainActivity> mainActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private ActivityTestRule<TeamActivity> teamActivityTestRule =
            new ActivityTestRule<>(TeamActivity.class);
    private ActivityTestRule<RoutesActivity> routesActivityTestRule =
            new ActivityTestRule<>(RoutesActivity.class);
    private ActivityTestRule<RouteDetailsActivity> routeDetailsActivityTestRule =
            new ActivityTestRule<>(RouteDetailsActivity.class);

    private Map<String, String> nameIdMap = new HashMap<>();

    @Before
    public void setup() {
        Intents.init();
        WWRApplication.setMapsMediator(new TestMapsMediator());
        WWRApplication.setDatabase(new TestDatabaseService());

        db = WWRApplication.getDatabase();
        user = WWRApplication.getUser();
        user.setEmail("wwruser@gmail.com");
        user.setHeight(61);
        team = user.getTeam();
        team.setId("ViewTeamTest");

        team.getMembers().clear();
        user.getInvites().clear();
        user.getRoutes().clear();
        user.getTeamRoutes().clear();
    }

    @After
    public void tearDown() {
        if (mainActivityTestRule.getActivity() != null) {
            mainActivityTestRule.getActivity().finish();
        }
        if (teamActivityTestRule.getActivity() != null) {
            teamActivityTestRule.getActivity().finish();
        }
        if (routesActivityTestRule.getActivity() != null) {
            routesActivityTestRule.getActivity().finish();
        }
        if (routeDetailsActivityTestRule.getActivity() != null) {
            routeDetailsActivityTestRule.getActivity().finish();
        }
        Intents.release();
    }

    @Given("a team activity")
    public void aTeamActivity() {
        System.out.println("STARTING TEAM_ACTIVITY");
        teamActivityTestRule.launchActivity(null);
        assertThat(teamActivityTestRule.getActivity(), notNullValue());
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
    public void theUserHasNoTeamMembers() {
        user.getTeam().getMembers().clear();
    }

    @Given("the user has team members")
    public void theUserHasTeamMembers() {
        team.getMembers().add(new TeamMember("Team 32", "email0", Color.RED));
        team.getMembers().get(0).setStatus(TeamMember.STATUS_MEMBER);
        db.addTeammateRoutesListener(user, team.getMembers().get(0));

        team.getMembers().add(new TeamMember("CSE 110", "email1", Color.GREEN));
        team.getMembers().get(1).setStatus(TeamMember.STATUS_MEMBER);
        db.addTeammateRoutesListener(user, team.getMembers().get(1));

        team.getMembers().add(new TeamMember("Project Team", "email2", Color.BLUE));
        team.getMembers().get(2).setStatus(TeamMember.STATUS_MEMBER);
        db.addTeammateRoutesListener(user, team.getMembers().get(2));
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

    @Given("a routes activity")
    public void aRoutesActivity() {
        System.out.println("STARTING ROUTES_ACTIVITY");
        routesActivityTestRule.launchActivity(null);
        assertThat(routesActivityTestRule.getActivity(), notNullValue());
    }

    @When("the user clicks the team routes button")
    public void theUserClicksTheTeamRoutesButton() {
        onView(withId(R.id.routesTeamButton)).perform(click());
    }

    @Then("no team routes are displayed")
    public void noTeamRoutesAreDisplayed() {
        onView(withId(R.id.teamRoutesRowName)).check(doesNotExist());
    }

    @And("the user returns to the routes screen")
    public void theUserReturnsToTheRoutesScreen() {
        onView(withId(R.id.teamRoutesBackButton)).perform(click());
    }

    @Then("team routes are displayed")
    public void teamRoutesAreDisplayed() {
        for (TeamMember member : team.getMembers()) {
            onView(allOf(withId(R.id.teamRoutesRowName), withText(member.getName() + "_route")))
                    .check(matches(isDisplayed()));
        }
    }

    @And("the user has routes")
    public void theUserHasRoutes() {
        Route route0 = new UserRoute(0, "UserRoute0");
        Route route1 = new UserRoute(0, "UserRoute1");
        user.getRoutes().createRoute(route0);
        user.getRoutes().createRoute(route1);
    }

    @And("the user's team members have routes")
    public void theUsersTeamMembersHaveRoutes() {
        for (TeamMember member : team.getMembers()) {
            Route route = new UserRoute(0, member.getName() + "_route");
            WWRApplication.getDatabase().addRoute(new TeamRoute(route, member));
        }
    }

    @And("the user has no invites")
    public void theUserHasNotBeenInvitedToAnyTeam() {
        user.getInvites().clear();
    }

    @Then("the invite button is not displayed")
    public void theInviteButtonIsNotDisplayed() {
        onView(withId(R.id.inviteButton)).check(matches(
                withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
    }

    @And("the user has an invite")
    public void theUserHasAnInviteToATeam() {
        invitedTeam = new Team();
        invitedTeam.setId("invitedTeam");
        inviter = new TeamMember("inviter", "inviter", Color.MAGENTA);
        invite = new Invite(user.getEmail(), invitedTeam.getId(), inviter.getEmail());

        user.getInvites().add(invite);
        invitedTeam.getMembers().add(inviter);
        invitedTeam.getMembers().add(new TeamMember("user", user.getEmail(), Color.CYAN));
    }

    @When("the user clicks the invite button")
    public void theUserClicksTheInviteButton() {
        onView(withId(R.id.inviteButton))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
                .perform(click());
    }

    @And("the user clicks the accept button")
    public void theUserClicksTheAcceptButton() {
        onView(withId(R.id.acceptInviteButton)).perform(click());
    }

    @Then("the user is added to the team as a member")
    public void theUserIsAddedToTheTeam() {
        assertEquals(TeamMember.STATUS_MEMBER,
                invitedTeam.findMemberById(user.getEmail()).getStatus());
    }

    @And("the user's invite is removed")
    public void theUsersInviteIsRemoved() {
        assertEquals(0, user.getInvites().size());
    }

    @And("the user's team becomes the inviting team")
    public void theUsersTeamBecomesTheInvitingTeam() {
        assertEquals(invitedTeam, user.getTeam());
    }

    @And("the user clicks the decline button")
    public void theUserClicksTheDeclineButton() {
        onView(withId(R.id.declineInviteButton)).perform(click());
    }

    @And("the user is removed from the inviting team")
    public void theUserIsRemovedFromTheInvitingTeam() {
        assertNull(invitedTeam.findMemberById(user.getEmail()));
    }

    @And("the user's team is empty")
    public void theUserSTeamIsEmpty() {
        assertEquals(0, user.getTeam().getMembers().size());
    }

    @And("the user's team members have previously walked their routes")
    public void theUserSTeamMembersHavePreviouslyWalkedTheirRoutes() {
        for (TeamRoute route : user.getTeamRoutes()) {
            route.setSteps(10);
            route.setStartDate(LocalDateTime.of(10, 10, 10, 10, 10));
            route.setDuration(LocalTime.of(10, 10, 10));
        }
    }

    @And("the user has never walked a team route")
    public void theUserHasNeverWalkedATeamRoute() {
        for (TeamRoute route : user.getTeamRoutes()) {
            Context context = user.getContext();
            String docID = route.getDocID();
            RouteData.saveTeamRouteSteps(context, docID, DataConstants.INT_NOT_FOUND);
            RouteData.saveTeamRouteDate(context, docID, DataConstants.STR_NOT_FOUND);
            RouteData.saveTeamRouteTime(context, docID, DataConstants.STR_NOT_FOUND);
        }
    }

    @Then("the user's team member's walk data is displayed")
    public void theUserSTeamMembersWalkDataIsDisplayed() {
        onView(withId(R.id.detailsRouteSteps)).check(matches(withText(Integer.toString(10))));
    }

    @And("the user has previously walked the team routes")
    public void theUserHasPreviouslyWalkedTheTeamRoutes() {
        for (TeamRoute route : user.getTeamRoutes()) {
            user.updateTeamRoute(route, 5, LocalTime.of(5, 5, 5),
                    LocalDateTime.of(5, 5, 5, 5, 5));
        }
    }

    @Then("the user's walk data is displayed")
    public void theUserSWalkDataIsDisplayed() {
        onView(withId(R.id.detailsRouteSteps)).check(matches(withText(Integer.toString(5))));
    }

    @And("the user clicks a team route")
    public void theUserClicksATeamRoute() {
        TeamRoute route = user.getTeamRoutes().get(0);
        TeamMember member = route.getCreator();
        onView(allOf(withId(R.id.teamRoutesRowName), withText(member.getName() + "_route")))
                .perform(click());
    }

    @And("the user returns to the team routes screen")
    public void theUserReturnsToTheTeamRoutesScreen() {
        onView(withId(R.id.detailsBackButton)).perform(click());
    }

    @And("the user's route is not a favorite")
    public void theUserSRouteIsNotAFavorite() {
        user.getRoutes().getRoute(0).setFavorite(false);
    }

    @When("the user clicks the favorite button for a route")
    public void theUserClicksTheFavoriteButtonForARoute() {
        onView(withId(R.id.routeRowFavorite)).perform(click());
    }

    @Then("the route is favorited")
    public void theRouteIsFavorited() {
        onView(withId(R.id.routeRowFavorite))
                .check(matches(new BackgroundColorMatcher(Route.FAV_COLOR)));
        assertTrue(user.getRoutes().getRoute(0).isFavorite());
        assertTrue(RouteData.retrieveFavorite(user.getContext(),
                user.getRoutes().getRoute(0).getID()));
    }

    @And("the user's route is a favorite")
    public void theUserSRouteIsAFavorite() {
        user.getRoutes().getRoute(0).setFavorite(true);
    }

    @Then("the route is un-favorited")
    public void theRouteIsUnFavorited() {
        onView(withId(R.id.routeRowFavorite))
                .check(matches(new BackgroundColorMatcher(Route.UNFAV_COLOR)));
        assertFalse(user.getRoutes().getRoute(0).isFavorite());
        assertFalse(RouteData.retrieveFavorite(user.getContext(),
                user.getRoutes().getRoute(0).getID()));
    }

    @Given("the user has a route")
    public void theUserHasARoute() {
        user.getRoutes().createRoute(new UserRoute(0, "UserRoute"));
    }

    @And("the user's team has a scheduled walk")
    public void theUserSTeamHasAScheduledWalk() {
        team.setScheduledWalk(new ScheduledWalk(new UserRoute(0, "ScheduledWalk"),
                LocalDateTime.of(1, 1, 1, 1, 1), user.getEmail(), team));
    }

    @And("the user is not the creator of the scheduled walk")
    public void theUserIsNotTheCreatorOfTheScheduledWalk() {
        team.getScheduledWalk().setCreatorId("walkScheduler@gmail.com");
    }

    @When("the user clicks the scheduled walk button")
    public void theUserClicksTheScheduledWalkButton() {
        onView(withId(R.id.plannedWalkButton)).perform(click());
    }

    @And("the user clicks the scheduled walk accept button")
    public void theUserClicksTheScheduledWalkAcceptButton() {
        assertNotEquals(team.getScheduledWalk().getCreatorId(), user.getEmail());
        onView(withId(R.id.buttonAcceptRoute)).perform(click());
    }

    @Then("the user accepts the scheduled walk")
    public void theUserAcceptsTheScheduledWalk() {
        assertEquals(ScheduledWalk.ACCEPTED, scheduledWalkUserStatus);
        assertEquals(ScheduledWalk.ACCEPTED,
                team.getScheduledWalk().retrieveResponse(user.getEmail()));
    }

    @And("the user goes back to the home screen")
    public void theUserGoesBackToTheHomeScreen() {
        onView(withId(R.id.scheduleToHomeButton)).perform(click());
    }

    @And("the user is the creator of the scheduled walk")
    public void theUserIsTheCreatorOfTheScheduledWalk() {
        team.getScheduledWalk().setCreatorId(user.getEmail());
    }

    @And("the user clicks the schedule button")
    public void theUserClicksTheScheduleButton() {
        onView(withId(R.id.buttonSchedule)).perform(click());
    }

    @Then("the walk is scheduled")
    public void theWalkIsScheduled() {
        assertEquals(team.getScheduledWalk().getStatus(), ScheduledWalk.SCHEDULED);
        onView(withId(R.id.schedHeader))
                .check(matches(withText(team.getScheduledWalk().retrieveStringStatus())));
    }

    @And("the user clicks the withdraw button")
    public void theUserClicksTheWithdrawButton() {
        onView(withId(R.id.buttonWithdraw)).perform(click());
    }

    @Then("the walk is withdrawn")
    public void theWalkIsWithdrawn() {
        assertEquals(ScheduledWalk.WITHDRAWN, scheduledWalkStatus);
        assertNull(user.getTeam().getScheduledWalk());
    }

    @And("the user clicks the scheduled walk decline \\(bad time) button")
    public void theUserClicksTheScheduledWalkDeclineBadTimeButton() {
        onView(withId(R.id.buttonBadTime)).perform(click());
    }

    @Then("the user declines the scheduled walk due to a bad time")
    public void theUserDeclinesTheScheduledWalkDueToABadTime() {
        assertEquals(ScheduledWalk.DECLINED_BAD_TIME, scheduledWalkUserStatus);
        assertEquals(ScheduledWalk.DECLINED_BAD_TIME,
                team.getScheduledWalk().retrieveResponse(user.getEmail()));
    }

    @And("the user clicks the scheduled walk decline \\(bad route) button")
    public void theUserClicksTheScheduledWalkDeclineBadRouteButton() {
        onView(withId(R.id.buttonBadRoute)).perform(click());
    }

    @Then("the user declines the scheduled walk due to a bad route")
    public void theUserDeclinesTheScheduledWalkDueToABadRoute() {
        assertEquals(ScheduledWalk.DECLINED_BAD_ROUTE, scheduledWalkUserStatus);
        assertEquals(ScheduledWalk.DECLINED_BAD_ROUTE,
                team.getScheduledWalk().retrieveResponse(user.getEmail()));
    }

    @And("the user has previously walked the route")
    public void theUserHasPreviouslyWalkedTheRoute() {
        Route route = user.getRoutes().getRoute(0);
        route.setSteps(10);
        route.setStartDate(LocalDateTime.of(1,1,1,1,1));
        route.setDuration(LocalTime.of(0,0,0));
    }

    @Then("a previously-walked icon is displayed on the route entry")
    public void aPreviouslyWalkedIconIsDisplayedOnTheRouteEntry() {
        onView(withId(R.id.routeWalkedIcon)).check(matches(isDisplayed()));
    }

    @When("the user clicks the route")
    public void theUserClicksTheRoute() {
        onView(withId(R.id.routeRowName)).perform(click());
    }

    @Then("a previously-walked icon is displayed on the route details")
    public void aPreviouslyWalkedIconIsDisplayedOnTheRouteDetails() {
        onView(withId(R.id.detailsWalkedIcon)).check(matches(isDisplayed()));
    }

    @And("the user returns to the routes screen from the details screen")
    public void theUserReturnsToTheRoutesScreenFromTheDetailsScreen() {
        onView(withId(R.id.detailsBackButton)).perform(click());
    }

    @And("the user has never walked the route")
    public void theUserHasNeverWalkedTheRoute() {
        Route route = user.getRoutes().getRoute(0);
        route.setSteps(-1);
        route.setStartDate(null);
        route.setDuration(null);
    }

    @Then("a previously-walked icon is not displayed on the route entry")
    public void aPreviouslyWalkedIconIsNotDisplayedOnTheRouteEntry() {
        onView(withId(R.id.routeWalkedIcon)).check(matches(not(isDisplayed())));
    }

    @Then("a previously-walked icon is not displayed on the route details")
    public void aPreviouslyWalkedIconIsNotDisplayedOnTheRouteDetails() {
        onView(withId(R.id.detailsWalkedIcon)).check(matches(not(isDisplayed())));
    }

    @And("the user's team does not have a scheduled or proposed walk")
    public void theUserSTeamDoesNotHaveAScheduledProposedWalk() {
        team.setScheduledWalk(null);
    }

    @When("the user clicks the propose walk button")
    public void theUserClicksTheProposeWalkButton() {
        onView(withId(R.id.detailsProposeWalkButton)).perform(click());
    }

    @And("the user fills in the time and date of the proposed walk")
    public void theUserFillsInTheTimeAndDateOfTheProposedWalk() {
        onView(withId(R.id.proposeWalkPositiveButton)).perform(click());
        onView(withId(R.id.proposeWalkPositiveButton)).perform(click());
    }

    @Then("the walk is proposed")
    public void theWalkIsProposed() {
        assertNotNull(team.getScheduledWalk());
        assertEquals(user.getRoutes().getRoute(0),
                team.getScheduledWalk().getRouteAdapter().toRoute());
    }

    @And("the proposed walk details are displayed")
    public void theProposedWalkDetailsAreDisplayed() {
        onView(withId(R.id.schedHeader)).check(matches(isDisplayed()));
    }

    @And("the user returns to the previous screen from the proposed walk screen")
    public void theUserReturnsToThePreviousScreenFromTheProposedWalkScreen() {
        onView(withId(R.id.scheduleToHomeButton)).perform(click());
    }

    @Then("an error message is displayed for proposed walk")
    public void anErrorMessageIsDisplayedForProposedWalk() { }

    @And("the user clicks the starting point")
    public void theUserClicksTheStartingPoint() {
        onView(withId(R.id.schedStartingPoint)).perform(click());
    }

    @Then("Google Maps is launched with the starting point as the search query")
    public void googleMapsIsLaunchedWithTheStartingPointAsTheSearchQuery() {
        assertTrue(mapsLaunched);
    }

    @And("the scheduled walk has a starting point")
    public void theScheduledWalkHasAStartingPoint() {
        Route route = team.getScheduledWalk().retrieveRoute();
        route.setStartingPoint("Start Here");
        team.getScheduledWalk().setRoute(route);
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

    private class TestMapsMediator extends MapsMediator {
        @Override
        public void launchMaps(Activity activity) {
            mapsLaunched = true;
        }
    }


    private class TestDatabaseService implements DatabaseService {
        private Set<TeamMember> membersWithUserAsListener = new HashSet<>();

        @Override
        public void addRoute(Route route) {
            if (route instanceof TeamRoute &&
                    membersWithUserAsListener.contains(((TeamRoute) route).getCreator())) {
                user.getTeamRoutes().add((TeamRoute)route);
            }
        }

        @Override
        public void updateRoute(Route route) { }

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
        public Task<?> updateTeam(Team team) {
            if (team.getScheduledWalk() != null &&
                    ! user.getEmail().equals(team.getScheduledWalk().getCreatorId())) {
                scheduledWalkUserStatus = team.getScheduledWalk().retrieveResponse(user.getEmail());
                scheduledWalkStatus = team.getScheduledWalk().getStatus();
            } else {
                scheduledWalkUserStatus = ScheduledWalk.NO_RESPONSE;
                scheduledWalkStatus = ScheduledWalk.WITHDRAWN;
            }

            return null;
        }

        @Override
        public void addTeammateRoutesListener(User listener, TeamMember teammate) {
            if (listener == user) {
                membersWithUserAsListener.add(teammate);
            }
        }

        @Override
        public void getRoutes(List<Route> routes) {

        }

        @Override
        public ListenerRegistration addTeamListener(Team team) {
            if (invitedTeam != null && team.getId().equals(invitedTeam.getId())) {
                team.getMembers().addAll(invitedTeam.getMembers());
            }
            return null;
        }

        public void declineInvite(Invite invite) {
            user.getInvites().remove(invite);
            invitedTeam.removeMemberById(invite.getInvitedMemberId());
        }

        @Override
        public void acceptInvite(Invite invite) {
            user.getInvites().remove(invite);
            user.setTeam(invitedTeam);
            team = invitedTeam;
            invitedTeam.findMemberById(user.getEmail()).setStatus(TeamMember.STATUS_MEMBER);
        }

        @Override
        public void removeTeammatesListener(ListenerRegistration listener) { }

        @Override
        public void addInvitesListener(User listener) { }
    }

}