package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.cse110_project.util.RouteListAdapter;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.temporal.ChronoUnit;

public class RoutesActivity extends AppCompatActivity{
    private final static String TAG = "RoutesActivity";
    private final static String MONTH_DAY_SEPARATOR = " ";
    private User user;
    
    private String[] nameArray;
    private String[] startPtArray;
    private String[] stepsArray;
    private String[] milesArray;
    private String[] timeArray;
    private String[] dateArray;
    private String[] flatHillyArray;
    private String[] loopOutBackArray;
    private String[] streetsTrailArray;
    private String[] evenUnevenArray;
    private String[] difficultyArray;
    private String[] favArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        user = WWRApplication.getUser();

        int arrLen = user.getRoutes().length();
        nameArray = new String[arrLen];
        startPtArray = new String[arrLen];
        stepsArray = new String[arrLen];
        milesArray = new String[arrLen];
        timeArray = new String[arrLen];
        dateArray = new String[arrLen];
        flatHillyArray = new String[arrLen];
        loopOutBackArray = new String[arrLen];
        streetsTrailArray = new String[arrLen];
        evenUnevenArray = new String[arrLen];
        difficultyArray = new String[arrLen];
        favArray = new String[arrLen];

        // To other activities
        Button homeButton = findViewById(R.id.routesHomeButton);
        homeButton.setOnClickListener(v -> finish());

        // To team routes page
        Button teamButton = findViewById(R.id.routesTeamButton);
        teamButton.setOnClickListener(v -> launchTeamRoutesActivity());

        Button newRouteButton = findViewById(R.id.routesNewRouteButton);
        newRouteButton.setOnClickListener(v ->
                (new SaveRouteDialog(this, this, 0,null, null))
                        .inputRouteDataDialog());
    }

    // to team routes
    public void launchTeamRoutesActivity() {
        Intent intent = new Intent(this, TeamRoutesActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchRoutesData();

        // Set up list view
        RouteListAdapter adapter = new RouteListAdapter(this, nameArray, startPtArray,
                stepsArray, milesArray, timeArray, dateArray, flatHillyArray, streetsTrailArray,
                loopOutBackArray, evenUnevenArray, difficultyArray, favArray);

        ListView listView = findViewById(R.id.listviewID);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, RouteDetailsActivity.class);
            intent.putExtra(RouteDetailsActivity.ROUTE_INDEX_KEY, position);
            startActivity(intent);
        });
    }

    public void fetchRoutesData(){
        Log.d(TAG, "Fetching routes data");
        RouteList routes = user.getRoutes();
        routes.sortByName();
        for (int i = 0; i < routes.length(); i++) {
            Route r = routes.getRoute(i);
            nameArray[i] = r.getName();
            startPtArray[i] = r.getStartingPoint();
            stepsArray[i] = String.valueOf(r.getSteps());
            milesArray[i] = MilesCalculator.formatMiles(r.getMiles(user.getHeight()));
            flatHillyArray[i] = r.getFlatVsHilly();
            loopOutBackArray[i] = r.getLoopVsOutBack();
            streetsTrailArray[i] = r.getStreetsVsTrail();
            evenUnevenArray[i] = r.getEvenVsUneven();
            difficultyArray[i] = r.getDifficulty();
            favArray[i] = r.isFavorite() ? Route.FAV : Route.NO_DATA;

            if (r.getStartDate() != null) {
                timeArray[i] = r.getDuration().truncatedTo(ChronoUnit.MINUTES).toString();
                dateArray[i] = r.getStartDate().getMonth() + MONTH_DAY_SEPARATOR
                        + r.getStartDate().getDayOfMonth();
            }
        }
    }
}