package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.team.ScheduledWalk;

import com.example.cse110_project.team.WalkScheduler;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class ScheduledDetails extends AppCompatActivity {

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
        setContentView(R.layout.activity_scheduled_details);

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
            acceptButton.setOnClickListener(v -> acceptWalk());
            declineRouteButton.setOnClickListener(v -> declineWalkBadRoute());
            declineTimeButton.setOnClickListener(v -> declineWalkBadTime());

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
        TextView routeStartingPoint = findViewById(R.id.schedStartingPoint);
        routeStartingPoint.setText(route.getStartingPoint());

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
        String acceptedUsers = "";
        String declinedUsersBadRoute = "";
        String declinedUsersBadTime = "";

        TextView accepted = findViewById(R.id.peopleThatAccepted);
        TextView declinedBadRoute = findViewById(R.id.peopleThatDeclinedBadRoute);
        TextView declinedBadTime = findViewById(R.id.peopleThatDeclinedBadTime);

        Map<String, Integer> responses = user.getTeam().getScheduledWalk().getResponses();
        for (Map.Entry<String,Integer> entry : responses.entrySet()) {
            if(entry.getValue() == ACCEPTED){
                acceptedUsers =  acceptedUsers +
                        user.getTeam().findMemberById(entry.getKey()).getName() + "/" ;
            }
            else if (entry.getValue() == DECLINED_BAD_TIME) {
                declinedUsersBadTime =  declinedUsersBadTime +
                        user.getTeam().findMemberById(entry.getKey()).getName() + "/" ;
            }
            else {
                declinedUsersBadRoute = declinedUsersBadRoute +
                        user.getTeam().findMemberById(entry.getKey()).getName() + "/" ;
            }
        }

        accepted.setText(acceptedUsers);
        declinedBadRoute.setText(declinedUsersBadRoute);
        declinedBadTime.setText(declinedUsersBadTime);


        // set the status of walk
        TextView scheduledHeader = findViewById(R.id.schedHeader);
        scheduledHeader.setText(user.getTeam().getScheduledWalk().retrieveStringStatus());
    }

    public void scheduleWalk() {
        Log.d(TAG, "Scheduling walk");
        scheduledWalk.schedule();
        TextView scheduledHeader = findViewById(R.id.schedHeader);
        scheduledHeader.setText(user.getTeam().getScheduledWalk().retrieveStringStatus());
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }

    public void withdrawWalk() {
        Log.d(TAG, "Withdrawing walk");
        scheduledWalk.withdraw();
        user.getTeam().setScheduledWalk(null);
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
        finish();
    }

    public void acceptWalk() {
        Log.d(TAG, "Accepting walk");
        scheduledWalk.accept(user.getEmail());
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }

    public void declineWalkBadTime() {
        Log.d(TAG, "Declining walk (bad time)");
        scheduledWalk.declineBadTime(user.getEmail());
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }

    public void declineWalkBadRoute() {
        Log.d(TAG, "Declining walk (bad route)");
        scheduledWalk.declineBadRoute(user.getEmail());
        (new WalkScheduler()).updateScheduledWalk(user.getTeam());
    }
}
