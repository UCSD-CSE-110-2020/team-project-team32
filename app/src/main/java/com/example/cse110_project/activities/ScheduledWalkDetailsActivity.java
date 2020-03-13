package com.example.cse110_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.team.ScheduledWalk;

import com.example.cse110_project.team.TeamMember;
import com.example.cse110_project.team.WalkScheduler;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class ScheduledWalkDetailsActivity extends AppCompatActivity {
    public final static String SCHED_INDEX_KEY = "ROUTE_INDEX_KEY";
    private final static String TAG = "ScheduledDetailsActivity";
    public final static String CREATOR_KEY = "Creator Key";

    public static final int ACCEPTED = 1;
    public static final int DECLINED_BAD_TIME = -1;
    public static final int DECLINED_BAD_ROUTE = -2;

    User user; // this user
    private Route route; // route
    ScheduledWalk scheduledWalk;
    private boolean onCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_walk_details);

        user = WWRApplication.getUser();
        scheduledWalk = user.getTeam().getScheduledWalk();
        route = scheduledWalk.retrieveRoute();
        onCreator = getIntent().getBooleanExtra(CREATOR_KEY, false);

        View acceptButton = findViewById(R.id.buttonAcceptRoute);
        View declineRouteButton = findViewById(R.id.buttonBadRoute);
        View declineTimeButton = findViewById(R.id.buttonBadTime);
        View scheduleButton = findViewById(R.id.buttonSchedule);
        View withdrawButton = findViewById(R.id.buttonWithdraw);

        if (onCreator) {
            scheduleButton.setOnClickListener(v -> scheduleWalk());
            withdrawButton.setOnClickListener(v -> withdrawWalk());

            scheduleButton.setVisibility(View.VISIBLE);
            withdrawButton.setVisibility(View.VISIBLE);
            acceptButton.setVisibility(View.INVISIBLE);
            declineRouteButton.setVisibility(View.INVISIBLE);
            declineTimeButton.setVisibility(View.INVISIBLE);
        } else {

            acceptButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    acceptWalk();
                    updateUserResponses();
                }
            });

            declineRouteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    declineWalkBadRoute();
                    updateUserResponses();
                }
            });

            declineTimeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    declineWalkBadTime();
                    updateUserResponses();
                }
            });

            scheduleButton.setVisibility(View.INVISIBLE);
            withdrawButton.setVisibility(View.INVISIBLE);
            acceptButton.setVisibility(View.VISIBLE);
            declineRouteButton.setVisibility(View.VISIBLE);
            declineTimeButton.setVisibility(View.VISIBLE);
        }

        displayRouteData();
        // To other activities
        Button homeButton = findViewById(R.id.scheduleToHomeButton);
        homeButton.setOnClickListener(v -> finish());
    }

    public void displayRouteData() {
        Log.d(TAG, "Displaying route data");
        TextView routeName = findViewById(R.id.schedRouteName);
        routeName.setText(route.getName());

        // Set steps, miles, time, date only if route has been walked
        if (route.getStartDate() != null) {
            Log.d(TAG, "Walk data found");
            TextView routeSteps = findViewById(R.id.schedRouteSteps);
            routeSteps.setText(String.valueOf(route.getSteps()));
            TextView routeMiles = findViewById(R.id.schedRouteMiles);
            routeMiles.setText(MilesCalculator.formatMiles(route.getMiles(user.getHeight())));

            TextView routeTime = findViewById(R.id.schedRouteTime);
            routeTime.setText(route.getDuration().truncatedTo(ChronoUnit.MINUTES).toString());

            TextView routeDate = findViewById(R.id.schedStartDate);
            routeDate.setText(route.getStartDate().truncatedTo(ChronoUnit.DAYS)
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            TextView routeStartTime = findViewById(R.id.schedStartTime);
            routeStartTime.setText(route.getStartDate()
                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        }

        // Set optional features if existent
        Button routeStartingPoint = findViewById(R.id.schedStartingPoint);
        routeStartingPoint.setText(route.getStartingPoint());
        routeStartingPoint.setOnClickListener(v -> searchStartingPointInMaps());

        TextView routeFav = findViewById(R.id.schedFav);
        routeFav.setText(route.isFavorite() ? Route.FAV : Route.NO_DATA);

        TextView routeDifficulty = findViewById(R.id.schedDifficulty);
        routeDifficulty.setText(route.getDifficulty());
        TextView routeEvenUneven = findViewById(R.id.schedEvenUneven);
        routeEvenUneven.setText(route.getEvenVsUneven());

        TextView routeFlatHilly = findViewById(R.id.schedFlatHilly);
        routeFlatHilly.setText(route.getFlatVsHilly());
        TextView routeLoopOutBack = findViewById(R.id.schedLoopOutBack);
        routeLoopOutBack.setText(route.getLoopVsOutBack());

        TextView routeStreetsTrail = findViewById(R.id.schedStreetsTrail);
        routeStreetsTrail.setText(route.getStreetsVsTrail());
        TextView routeNotes = findViewById(R.id.schedNotes);
        routeNotes.setText(route.getNotes());

        // set the proposed date
        TextView scheduledDate = findViewById(R.id.schedDateTime);
        scheduledDate.setText(user.getTeam().getScheduledWalk().retrieveScheduledDate().truncatedTo(ChronoUnit.DAYS)
                .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));

        TextView scheduledTime = findViewById(R.id.schedTIme);
        scheduledTime.setText(user.getTeam().getScheduledWalk().retrieveScheduledDate()
                .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));

        // get responses
        updateUserResponses();


        // set the status of walk
        TextView scheduledHeader = findViewById(R.id.schedHeader);
        scheduledHeader.setText(user.getTeam().getScheduledWalk().retrieveStringStatus());
    }

    public void scheduleWalk() {
        scheduledWalk = user.getTeam().getScheduledWalk();
        Log.d(TAG, "Scheduling walk");
        scheduledWalk.schedule();
        TextView scheduledHeader = findViewById(R.id.schedHeader);
        scheduledHeader.setText(user.getTeam().getScheduledWalk().retrieveStringStatus());
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }

    public void withdrawWalk() {
        scheduledWalk = user.getTeam().getScheduledWalk();
        Log.d(TAG, "Withdrawing walk");
        scheduledWalk.withdraw();
        user.getTeam().setScheduledWalk(null);
        (new WalkScheduler()).removeScheduledWalk(user.getTeam());
        finish();
    }

    public void acceptWalk() {
        scheduledWalk = user.getTeam().getScheduledWalk();
        scheduledWalk.accept(user.getEmail());
        Log.d(TAG, "Accepting walk: "
                + scheduledWalk.getResponses().get(user.getEmail()));
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }

    public void declineWalkBadTime() {
        scheduledWalk = user.getTeam().getScheduledWalk();
        scheduledWalk.declineBadTime(user.getEmail());
        Log.d(TAG, "Declining walk (bad time): "
                + scheduledWalk.getResponses().get(user.getEmail()));
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }

    public void declineWalkBadRoute() {
        scheduledWalk = user.getTeam().getScheduledWalk();
        scheduledWalk.declineBadRoute(user.getEmail());
        Log.d(TAG, "Declining walk (bad route): "
                + scheduledWalk.getResponses().get(user.getEmail()));
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }


    // https://developers.google.com/maps/documentation/urls/android-intents#search_for_a_location
    private void searchStartingPointInMaps() {
        String searchText = route.getStartingPoint();
        if (searchText.length() > 0) {
            Intent mapIntent = WWRApplication.getMapsIntentBuilder().assembleIntent(searchText);
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                Log.d(TAG, "Launching Google Maps");
                WWRApplication.getMapsIntentBuilder().launchMaps(this);
            } else {
                Log.e(TAG, "Google Maps intent cannot be resolved");
            }
        }
    }

    public void updateUserResponses() {
        // get responses
        String acceptedUsers = "";
        String declinedUsersBadRoute = "";
        String declinedUsersBadTime = "";
        String NoResponseUsers = "";

        TextView accepted = findViewById(R.id.peopleThatAccepted);
        TextView declinedBadRoute = findViewById(R.id.peopleThatDeclinedBadRoute);
        TextView declinedBadTime = findViewById(R.id.peopleThatDeclinedBadTime);
        TextView NoResponse = findViewById(R.id.peopleWithNoResponse);


        Map<String, Integer> responses = user.getTeam().getScheduledWalk().getResponses();
        for (Map.Entry<String,Integer> entry : responses.entrySet()) {
            TeamMember member = user.getTeam().findMemberById(entry.getKey());
            if (member != null) {
                String initials = member.retrieveInitials();
                if (entry.getValue() == ACCEPTED) {
                    acceptedUsers = acceptedUsers + initials + ", ";
                } else if (entry.getValue() == DECLINED_BAD_TIME) {
                    declinedUsersBadTime = declinedUsersBadTime + initials + ", ";
                } else if (entry.getValue() == DECLINED_BAD_ROUTE) {
                    declinedUsersBadRoute = declinedUsersBadRoute + initials + ", ";
                } else {
                    NoResponseUsers = NoResponseUsers + initials + ", ";
                }
            }
        }

        accepted.setText(acceptedUsers);
        declinedBadRoute.setText(declinedUsersBadRoute);
        declinedBadTime.setText(declinedUsersBadTime);
        NoResponse.setText(NoResponseUsers);
    }
}
