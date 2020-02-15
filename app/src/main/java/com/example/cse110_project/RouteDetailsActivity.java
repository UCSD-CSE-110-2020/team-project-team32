package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.util.CurrentTimeTracker;
import com.example.cse110_project.util.CurrentWalkTracker;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public class RouteDetailsActivity extends AppCompatActivity {
    public final static String ROUTE_POS_KEY = "ROUTE_POSITION_KEY";
    private Route route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        // Extract route data
        int savedExtra = getIntent().getIntExtra(ROUTE_POS_KEY, 0);
        route = User.getRoutes(RouteDetailsActivity.this).getRoute(savedExtra);
        displayRouteData();

        Button launchToRouteScreen = findViewById(R.id.detailsBackButton);
        launchToRouteScreen.setOnClickListener(view -> finish());

        Button startWalkButton = findViewById(R.id.detailsStartWalkButton);
        startWalkButton.setOnClickListener(v -> launchWalkActivity());
    }

    public void displayRouteData() {
        TextView routeName = findViewById(R.id.routeNameDetail);
        routeName.setText(route.getName());

        // Set steps, miles, time, date only if route has been walked
        if (route.getStartDate() != null) {
            TextView routeSteps = findViewById(R.id.detailsRouteSteps);
            routeSteps.setText(String.valueOf(route.getSteps()));
            TextView routeMiles = findViewById(R.id.detailsRouteMiles);
            routeMiles.setText(MilesCalculator.formatMiles(route.getMiles(User.getHeight())));

            TextView routeTime = findViewById(R.id.detailsRouteTime);
            routeTime.setText(route.getDuration().truncatedTo(ChronoUnit.MINUTES).toString());

            TextView routeDate = findViewById(R.id.detailsRouteDate);
            routeDate.setText(route.getStartDate().truncatedTo(ChronoUnit.DAYS)
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            TextView routeStartTime = findViewById(R.id.detailsRouteStartTime);
            routeStartTime.setText(route.getStartDate()
                    .format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
        }

        // Set optional features if existent
        TextView routeStartingPoint = findViewById(R.id.detailsRouteStartingPoint);
        routeStartingPoint.setText(route.getStartingPoint());
        TextView routeDifficulty = findViewById(R.id.detailsDifficulty);
        routeDifficulty.setText(route.getDifficulty());

        TextView routeEvenUneven = findViewById(R.id.detailsEvenUneven);
        routeEvenUneven.setText(route.getEvenVsUneven());
        TextView routeFlatHilly = findViewById(R.id.detailsFlatHilly);
        routeFlatHilly.setText(route.getFlatVsHilly());

        TextView routeLoopOAB = findViewById(R.id.detailsLoopOAB);
        routeLoopOAB.setText(route.getLoopVsOAB());
        TextView routeStreetsTrail = findViewById(R.id.detailsStreetsTrail);
        routeStreetsTrail.setText(route.getStreetsVsTrail());

        TextView routeNotes = findViewById(R.id.detailsNotes);
        routeNotes.setText(route.getNotes());
    }


    public void launchWalkActivity() {
        CurrentWalkTracker.setInitial(User.getTotalSteps(), CurrentTimeTracker.getTime(),
                CurrentTimeTracker.getDate());

        Intent intent = new Intent(this, WalkActivity.class);
        intent.putExtra(WalkActivity.SAVED_ROUTE_KEY, true);
        intent.putExtra(WalkActivity.SAVED_ROUTE_ID_KEY, route.getID());
        startActivity(intent);

        // Return to Routes screen after finishing walk
        finish();
    }

}
