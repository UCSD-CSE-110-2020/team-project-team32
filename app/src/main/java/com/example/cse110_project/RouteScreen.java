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

import java.util.List;

public class RouteScreen extends AppCompatActivity{

    private String[] nameArray;
    private String[] FlatVsHilly;
    private String[] LoopVsOutBack;
    private String[] StreetVsTrail;
    private String[] EvenVsUneven;
    private String[] Difficulty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_screen);

        nameArray = new String[User.getRoutes(RouteScreen.this).length()];
        FlatVsHilly = new String[User.getRoutes(RouteScreen.this).length()];
        LoopVsOutBack = new String[User.getRoutes(RouteScreen.this).length()];
        StreetVsTrail = new String[User.getRoutes(RouteScreen.this).length()];
        EvenVsUneven = new String[User.getRoutes(RouteScreen.this).length()];
        Difficulty = new String[User.getRoutes(RouteScreen.this).length()];

        fetchRoutesData();

        CustomListAdapter adapter = new CustomListAdapter(this, nameArray, FlatVsHilly,
                StreetVsTrail, LoopVsOutBack, EvenVsUneven, Difficulty);

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
            for (int i = 0; i < routes.length(); i++) {
                nameArray[i] = routes.getRoute(i).getName();
                FlatVsHilly[i] = routes.getRoute(i).getFlatVsHilly();
                LoopVsOutBack[i] = routes.getRoute(i).getLoopVsOAB();
                StreetVsTrail[i] = routes.getRoute(i).getStreetsVsTrail();
                EvenVsUneven[i] = routes.getRoute(i).getEvenVsUneven();
                Difficulty[i] = routes.getRoute(i).getDifficulty();
            }
        }
    }
}