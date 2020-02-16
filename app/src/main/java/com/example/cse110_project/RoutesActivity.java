package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.cse110_project.util.CurrentWalkTracker;
import com.example.cse110_project.user_routes.RoutesListAdapter;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;
import com.example.cse110_project.util.MilesCalculator;

import java.time.temporal.ChronoUnit;

public class RoutesActivity extends AppCompatActivity{

    private String[] nameArray;
    private String[] startPtArray;
    private String[] stepsArray;
    private String[] milesArray;
    private String[] timeArray;
    private String[] dateArray;
    private String[] FlatVsHilly;
    private String[] LoopVsOutBack;
    private String[] StreetVsTrail;
    private String[] EvenVsUneven;
    private String[] Difficulty;
    private String[] favArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes);

        int arrLen = User.getRoutes(RoutesActivity.this).length();
        nameArray = new String[arrLen];
        startPtArray = new String[arrLen];
        stepsArray = new String[arrLen];
        milesArray = new String[arrLen];
        timeArray = new String[arrLen];
        dateArray = new String[arrLen];
        FlatVsHilly = new String[arrLen];
        LoopVsOutBack = new String[arrLen];
        StreetVsTrail = new String[arrLen];
        EvenVsUneven = new String[arrLen];
        Difficulty = new String[arrLen];
        favArray = new String[arrLen];

        fetchRoutesData();

        RoutesListAdapter adapter = new RoutesListAdapter(this, nameArray, startPtArray,
                stepsArray, milesArray, timeArray, dateArray, FlatVsHilly, StreetVsTrail,
                LoopVsOutBack, EvenVsUneven, Difficulty, favArray);

        ListView listView = findViewById(R.id.listviewID);
        listView.setAdapter(adapter);

        // Implementation of button event to route screen
        Button launchToHomeScreen = findViewById(R.id.routesHomeButton);
        Button newRoute = findViewById(R.id.routesNewRouteButton);

        newRoute.setOnClickListener(v -> (new SaveRouteDialog(this, this, 0,
                null, null)).inputRouteDataDialog());

        launchToHomeScreen.setOnClickListener(view -> finish());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RoutesActivity.this, RouteDetailsActivity.class);
            intent.putExtra(RouteDetailsActivity.ROUTE_POS_KEY, position);
            startActivity(intent);
        });
    }

    public void fetchRoutesData(){
        if (User.getRoutes(RoutesActivity.this) != null) {
            RouteList routes = User.getRoutes(RoutesActivity.this);
            routes.sortByName();
            for (int i = 0; i < routes.length(); i++) {
                Route r = routes.getRoute(i);
                nameArray[i] = r.getName();
                startPtArray[i] = r.getStartingPoint();
                stepsArray[i] = String.valueOf(r.getSteps());
                milesArray[i] = MilesCalculator.formatMiles(r.getMiles(User.getHeight()));
                FlatVsHilly[i] = r.getFlatVsHilly();
                LoopVsOutBack[i] = r.getLoopVsOAB();
                StreetVsTrail[i] = r.getStreetsVsTrail();
                EvenVsUneven[i] = r.getEvenVsUneven();
                Difficulty[i] = r.getDifficulty();
                favArray[i] = r.isFavorite() ? "FAV" : "";
                if (r.isFavorite()) {
                    System.out.println("Favorite");
                }

                if (r.getStartDate() != null) {
                    timeArray[i] = r.getDuration().truncatedTo(ChronoUnit.MINUTES).toString();
                    dateArray[i] = r.getStartDate().getMonthValue() + "-"
                            + r.getStartDate().getDayOfMonth();
                }
            }
        }
    }
}