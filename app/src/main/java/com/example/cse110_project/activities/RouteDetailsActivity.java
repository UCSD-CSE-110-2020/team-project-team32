package com.example.cse110_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.activities.dialogs.DialogObserver;
import com.example.cse110_project.activities.dialogs.DialogSubject;
import com.example.cse110_project.activities.dialogs.ProposeWalkDialog;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class RouteDetailsActivity extends AppCompatActivity implements DialogObserver {
    public final static String ROUTE_INDEX_KEY = "ROUTE_INDEX_KEY";
    private final static String TAG = "RouteDetailsActivity";
    private User user;
    private Route route;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        user = WWRApplication.getUser();

        // Extract route data
        int routeIndex = getIntent().getIntExtra(ROUTE_INDEX_KEY, 0);
        route = user.getRoutes().getRoute(routeIndex);
        displayRouteData();

        Button backButton = findViewById(R.id.detailsBackButton);
        backButton.setOnClickListener(v -> finish());

        Button startWalkButton = findViewById(R.id.detailsStartWalkButton);
        startWalkButton.setOnClickListener(v -> launchWalkActivity());

        Button proposeWalkButton = findViewById(R.id.detailsProposeWalkButton);
        proposeWalkButton.setOnClickListener(v -> launchProposeWalkDialog());
    }


    public void displayRouteData() {
        Log.d(TAG, "Displaying route data");
        TextView routeName = findViewById(R.id.detailsRouteName);
        routeName.setText(route.getName());

        ImageView walkedIcon = findViewById(R.id.detailsWalkedIcon);

        // Set steps, miles, time, date only if route has been walked
        if (route.getStartDate() != null) {
            Log.d(TAG, "Walk data found");
            TextView routeSteps = findViewById(R.id.detailsRouteSteps);
            routeSteps.setText(String.valueOf(route.getSteps()));
            TextView routeMiles = findViewById(R.id.detailsRouteMiles);
            routeMiles.setText(MilesCalculator.formatMiles(route.getMiles(user.getHeight())));

            TextView routeTime = findViewById(R.id.detailsRouteTime);
            routeTime.setText(route.getDuration().truncatedTo(ChronoUnit.MINUTES).toString());

            TextView routeDate = findViewById(R.id.detailsStartDate);
            routeDate.setText(route.getStartDate().truncatedTo(ChronoUnit.DAYS)
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            TextView routeStartTime = findViewById(R.id.detailsStartTime);
            routeStartTime.setText(route.getStartDate()
                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        }



        if (route.hasWalkData()) {
            walkedIcon.setVisibility(View.VISIBLE);
        } else {
            walkedIcon.setVisibility(View.INVISIBLE);
        }

        // Set optional features if existent
        TextView routeStartingPoint = findViewById(R.id.detailsStartingPoint);
        routeStartingPoint.setText(route.getStartingPoint());

        TextView routeFav = findViewById(R.id.detailsFav);
        routeFav.setText(route.isFavorite() ? Route.FAV : Route.NO_DATA);

        TextView routeDifficulty = findViewById(R.id.detailsDifficulty);
        routeDifficulty.setText(route.getDifficulty());
        TextView routeEvenUneven = findViewById(R.id.detailsEvenUneven);
        routeEvenUneven.setText(route.getEvenVsUneven());

        TextView routeFlatHilly = findViewById(R.id.detailsFlatHilly);
        routeFlatHilly.setText(route.getFlatVsHilly());
        TextView routeLoopOutBack = findViewById(R.id.detailsLoopOutBack);
        routeLoopOutBack.setText(route.getLoopVsOutBack());

        TextView routeStreetsTrail = findViewById(R.id.detailsStreetsTrail);
        routeStreetsTrail.setText(route.getStreetsVsTrail());
        TextView routeNotes = findViewById(R.id.detailsNotes);
        routeNotes.setText(route.getNotes());
    }


    public void launchWalkActivity() {
        Log.d(TAG, "Launching walk of route " + route.getName() + " with ID " + route.getID());
        LocalDateTime prevStartDate = route.getStartDate();

        Intent intent = new Intent(this, WalkActivity.class);
        intent.putExtra(WalkActivity.SAVED_ROUTE_KEY, true);
        intent.putExtra(WalkActivity.SAVED_ROUTE_ID_KEY, route.getID());
        startActivity(intent);

        // Return to Routes screen
        finish();
    }

    public void launchProposeWalkDialog() {
        if (user.getTeam().getScheduledWalk() != null) {
            Log.d(TAG, "Cannot create a second scheduled walk");
            Toast.makeText(this, "Your team already has a scheduled walk",
                    Toast.LENGTH_SHORT).show();
        } else {
            ProposeWalkDialog dialog = new ProposeWalkDialog(this, route);
            dialog.registerDialogObserver(this);
            dialog.launchProposeWalk(this, route);
        }
    }

    @Override
    public void onPositiveResultUpdate(DialogSubject subject) {
        Toast.makeText(this, "Successfully scheduled walk", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ScheduledWalkDetailsActivity.class);
        intent.putExtra(ScheduledWalkDetailsActivity.CREATOR_KEY, true);
        startActivity(intent);
    }

    @Override
    public void onNegativeResultUpdate(DialogSubject subject) { }
}