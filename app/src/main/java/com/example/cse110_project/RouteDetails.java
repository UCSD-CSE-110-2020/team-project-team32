package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RouteDetails extends AppCompatActivity {

    private int savedExtra;
    private String walkName;
    private String WalkDate;
    private int steps;
    private double Miles;
    private String Difficulty;
    private String SurfaceDifficulty;
    private String Surface;
    private String RunType;
    private String area;
    Route route;

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_details);

        savedExtra = getIntent().getIntExtra("Array_POSITION", 0);
        getRouteData();

        TextView routeName = (TextView) findViewById(R.id.routeNameDetail);
        TextView routeDate = (TextView) findViewById(R.id.WalkStartDate);
        TextView routeTime = (TextView) findViewById(R.id.WalkTime);
        TextView routeSteps = (TextView) findViewById(R.id.stepsWalked);
        TextView routeMiles = (TextView) findViewById(R.id.MilesWalked);
        TextView routeDifficulty = (TextView) findViewById(R.id.DifficultyPicked);
        TextView routesurfaceDifficulty = (TextView) findViewById(R.id.SurfaceDifficultyPicked);
        TextView routeSurface = (TextView) findViewById(R.id.surfaceFlatVsHillyPicked);
        TextView routeRunType = (TextView) findViewById(R.id.RunTypePicked);
        TextView routeArea = (TextView) findViewById(R.id.areaRouteDetailsPicked);


        routeName.setText(walkName);
        routeDate.setText(WalkDate.substring(0,10));
        routeTime.setText(WalkDate.substring(11));
        routeSteps.setText(Integer.toString(steps));
        routeMiles.setText(Double.toString(Miles));
        routeDifficulty.setText(Difficulty);
        routesurfaceDifficulty.setText(SurfaceDifficulty);
        routeSurface.setText(Surface);
        routeRunType.setText(RunType);
        routeArea.setText(area);


        final Button launchToRouteScreen = (Button) findViewById(R.id.button_backToRoutes);

        launchToRouteScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchToRouteActivity();
            }
        });
    }

    public void launchToRouteActivity() {
        Intent intent = new Intent(this, RouteScreen.class);
        startActivity(intent);
    }

    public void getRouteData() {
        route = User.getRoutes(RouteDetails.this).getRoute(savedExtra);
        walkName = route.getName();
        WalkDate = route.getStartDate().format(formatter);
        steps = route.getSteps();
        Miles = route.getMiles(User.getHeight());
        Difficulty = route.getRouteDifficulty();
        SurfaceDifficulty = route.getEvenVsUnevenSurface();
        Surface = route.getFlatVSHilly();
        RunType = route.getLoopVSOutBack();
        area = route.getStreetsVSTrail();

    }

}
