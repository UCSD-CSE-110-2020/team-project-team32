package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cse110_project.dialogs.ProposeWalkDialog;
import com.example.cse110_project.team.TeamRoute;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;


public class TeamRoutesDetailsActivity extends AppCompatActivity {
    public final static String ROUTE_INDEX_KEY = "ROUTE_INDEX_KEY";
    private final static String TAG = "RouteDetailsActivity";
    private User user;
    private TeamRoute route;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);
        user = WWRApplication.getUser();

        // Extract route data
        int routeIndex = getIntent().getIntExtra(ROUTE_INDEX_KEY, 0);
        route = user.getTeamRoutes().get(routeIndex);
        displayRouteData();

        Button backButton = findViewById(R.id.detailsBackButton);
        backButton.setOnClickListener(v -> finish());

        Button startWalkButton = findViewById(R.id.detailsStartWalkButton);
        startWalkButton.setOnClickListener(v -> launchWalkActivity());

        Button proposeWalkButton = findViewById(R.id.detailsProposeWalkButton);
        proposeWalkButton.setOnClickListener(v -> (new ProposeWalkDialog(this, route)).launchProposeWalk(this, route));
    }

    public void displayRouteData() {
        Log.d(TAG, "Displaying route data");
        TextView routeName = findViewById(R.id.detailsRouteName);
        routeName.setText(route.getName());

        TextView initials = findViewById(R.id.detailsInitials);
        initials.setText(route.getCreator().retrieveInitials());
        initials.setBackgroundColor(route.getCreator().getColor());
        initials.setVisibility(View.VISIBLE);

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

        ImageView walkedIcon = findViewById(R.id.detailsWalkedIcon);
        if (route.hasWalkData()) {
            walkedIcon.setVisibility(View.VISIBLE);
        } else {
            walkedIcon.setVisibility(View.INVISIBLE);
        }

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

        int routeIndex = getIntent().getIntExtra(ROUTE_INDEX_KEY, 0);

        Intent intent = new Intent(this, WalkActivity.class);
        intent.putExtra(WalkActivity.TEAM_ROUTES_KEY, true);
        intent.putExtra(WalkActivity.TEAM_ROUTE_DOC_ID_KEY, route.getDocID());
        startActivity(intent);
        // Return to Routes screen
        finish();
    }
}
