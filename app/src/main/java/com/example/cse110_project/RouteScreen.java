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
    //private boolean isFavorite;

    private ListView listView;
    private RouteList routes;

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
        //isFavorite = false;

        getRoutes();

        CustomListAdapter adapter = new CustomListAdapter(this, nameArray, FlatVsHilly,
                StreetVsTrail, LoopVsOutBack, EvenVsUneven, Difficulty);

        listView = findViewById(R.id.listviewID);
        listView.setAdapter(adapter);

        // listView = (ListView) findViewById(R.id.route_list_view);
        // Implementation of button event to route screen
        final Button launchToHomeScreen = findViewById(R.id.button_routeToHome);
        final Button NewRoute = findViewById(R.id.button_routeScreenNewRoute);

        NewRoute.setOnClickListener(v -> (new SaveRoute(this, this,
                CurrentWalkTracker.getWalkSteps(), CurrentWalkTracker.getWalkTime(),
                CurrentWalkTracker.getWalkDate()))
                .inputRouteDataDialog());

        launchToHomeScreen.setOnClickListener(view -> finish());

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(RouteScreen.this, RouteDetails.class);
            intent.putExtra("Array_POSITION", position);
            startActivity(intent);
        });



    }

    public void getRoutes(){
        if (User.getRoutes(RouteScreen.this) != null) {
            routes = User.getRoutes(RouteScreen.this);
            for(int i = 0; i <routes.length(); i++) {
                nameArray[i] = routes.getRoute(i).getName();
                FlatVsHilly[i] = routes.getRoute(i).getFlatVSHilly();
                LoopVsOutBack[i] = routes.getRoute(i).getLoopVSOutBack();
                StreetVsTrail[i] = routes.getRoute(i).getStreetsVSTrail();
                EvenVsUneven[i] = routes.getRoute(i).getEvenVsUnevenSurface();
                Difficulty[i] = routes.getRoute(i).getRouteDifficulty();
                //isFavorite = routes.getRoute(i).getRouteFavorite();
            }
        }
    }
}