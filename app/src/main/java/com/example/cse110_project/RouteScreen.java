package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.cse110_project.trackers.CurrentWalkTracker;
import com.example.cse110_project.user_routes.CustomListAdapter;
import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.RouteList;
import com.example.cse110_project.user_routes.User;

import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class RouteScreen extends AppCompatActivity{

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_screen);

        int arrLen = User.getRoutes(RouteScreen.this).length();
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

        fetchRoutesData();

        CustomListAdapter adapter = new CustomListAdapter(this, nameArray, startPtArray,
                stepsArray, milesArray, timeArray, dateArray, FlatVsHilly, StreetVsTrail,
                LoopVsOutBack, EvenVsUneven, Difficulty);

        ListView listView = findViewById(R.id.listviewID);
        listView.setAdapter(adapter);

        // Implementation of button event to route screen
        Button launchToHomeScreen = findViewById(R.id.button_routeToHome);
        Button newRoute = findViewById(R.id.button_routeScreenNewRoute);

        newRoute.setOnClickListener(v -> (new SaveRoute(this, this,
                CurrentWalkTracker.getWalkSteps(), CurrentWalkTracker.getWalkTime(),
                CurrentWalkTracker.getWalkDate()))
                .inputRouteDataDialog());

        launchToHomeScreen.setOnClickListener(view -> finish());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RouteScreen.this, RouteDetails.class);
            intent.putExtra(RouteDetails.ROUTE_POS_KEY, position);
            startActivity(intent);
        });



    }

    public void fetchRoutesData(){
        if (User.getRoutes(RouteScreen.this) != null) {
            RouteList routes = User.getRoutes(RouteScreen.this);
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

                if (r.getStartDate() != null) {
                    timeArray[i] = r.getDuration().truncatedTo(ChronoUnit.MINUTES).toString();
                    dateArray[i] = r.getStartDate().getMonthValue() + "-"
                            + r.getStartDate().getDayOfMonth();
                }
            }
        }
    }
}