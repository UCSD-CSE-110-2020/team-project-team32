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

    String nameArray[];
    String FlatVsHilly[];
    String LoopVsOutBack[];
    String StreetVsTrail[];
    String EvenVsUneven[];
    String Difficulty[];

    ListView listView;
    RouteList routes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_screen);

        nameArray= new String[User.getRoutes(RouteScreen.this).length()];
        FlatVsHilly = new String[User.getRoutes(RouteScreen.this).length()];
        LoopVsOutBack = new String[User.getRoutes(RouteScreen.this).length()];
        StreetVsTrail = new String[User.getRoutes(RouteScreen.this).length()];
        EvenVsUneven = new String[User.getRoutes(RouteScreen.this).length()];
        Difficulty = new String[User.getRoutes(RouteScreen.this).length()];

        getRoutes();

        CustomListAdapter adapter = new CustomListAdapter(this, nameArray, FlatVsHilly ,StreetVsTrail,
                LoopVsOutBack, EvenVsUneven, Difficulty);

        listView = (ListView) findViewById(R.id.listviewID);
        listView.setAdapter(adapter);

        final Button launchToHomeScreen = (Button) findViewById(R.id.button_routeToHome);
        final Button NewRoute = (Button) findViewById(R.id.button_routeScreenNewRoute);

        NewRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new SaveRoute(RouteScreen.this, CurrentWalkTracker.getWalkSteps(),
                        CurrentWalkTracker.getWalkTime(), CurrentWalkTracker.getWalkDate()))
                        .inputRouteDataDialog();
            };
        });

        launchToHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchToHomeActivity();
            }
        });

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(RouteScreen.this, RouteDetails.class);
                int getArrayPosition = position;
                intent.putExtra("Array_POSITION", getArrayPosition);
                startActivity(intent);
            }
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
            }
        }
    }

    public void launchToHomeActivity() {
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
    }
}