package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.util.RouteListAdapter;
import com.example.cse110_project.user_routes.UserRoute;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.temporal.ChronoUnit;

public class RoutesActivity extends AppCompatActivity{
    private final static String TAG = "RoutesActivity";
    private User user;

    private String[] nameArray;
    private RouteList routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);
        user = WWRApplication.getUser();

        int arrLen = user.getRoutes().length();
        nameArray = new String[arrLen];

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
        RouteListAdapter adapter = new RouteListAdapter(this, nameArray, routes);

        ListView listView = findViewById(R.id.routesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, RouteDetailsActivity.class);
            intent.putExtra(RouteDetailsActivity.ROUTE_INDEX_KEY, position);
            startActivity(intent);
        });
    }

    public void fetchRoutesData(){
        Log.d(TAG, "Fetching routes data");
        routes = user.getRoutes();
        routes.sortByName();
        for (int i = 0; i < routes.length(); i++) {
            Route r = routes.getRoute(i);
            nameArray[i] = r.getName();
        }
    }
}
