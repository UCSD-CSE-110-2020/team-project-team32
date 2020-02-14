package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.trackers.CurrentTimeTracker;
import com.example.cse110_project.trackers.CurrentWalkTracker;
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
    private String walkNotes;
    Route route;

    static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_details);

        savedExtra = getIntent().getIntExtra("Array_POSITION", 0);
        getRouteData();

        TextView routeName = findViewById(R.id.routeNameDetail);
        TextView routeDate = findViewById(R.id.WalkStartDate);
        TextView routeTime = findViewById(R.id.WalkTime);
        TextView routeSteps = findViewById(R.id.stepsWalked);
        TextView routeMiles = findViewById(R.id.MilesWalked);
        TextView routeDifficulty = findViewById(R.id.DifficultyPicked);
        TextView routesurfaceDifficulty = findViewById(R.id.SurfaceDifficultyPicked);
        TextView routeSurface = findViewById(R.id.surfaceFlatVsHillyPicked);
        TextView routeRunType = findViewById(R.id.RunTypePicked);
        TextView routeArea = findViewById(R.id.areaRouteDetailsPicked);
        TextView routeNotes = findViewById(R.id.walkNotesWritten); // notes


        routeName.setText(walkName);
        routeNotes.setText(walkNotes);
        if(route.getStartDate() != null) {
            routeDate.setText(WalkDate.substring(0, 10));
            routeTime.setText(WalkDate.substring(11));
        } else {
            routeDate.setText(WalkDate);
            routeTime.setText(WalkDate);
        }

        routeSteps.setText(Integer.toString(steps));
        routeMiles.setText(Double.toString(Miles));
        routeDifficulty.setText(Difficulty);
        routesurfaceDifficulty.setText(SurfaceDifficulty);
        routeSurface.setText(Surface);
        routeRunType.setText(RunType);
        routeArea.setText(area);


        Button launchToRouteScreen = findViewById(R.id.button_backToRoutes);
        launchToRouteScreen.setOnClickListener(view -> finish());

        Button startWalkButton = findViewById(R.id.detailsStartWalkButton);
        startWalkButton.setOnClickListener(v -> launchWalkActivity());
    }

    public void getRouteData() {
        route = User.getRoutes(RouteDetails.this).getRoute(savedExtra);
        walkName = route.getName();
        walkNotes = route.getRouteNotes();

        if(route.getStartDate() != null) {
            WalkDate = route.getStartDate().format(formatter);
        } else{
            WalkDate = "N/A";
        }

        steps = route.getSteps();
        Miles = route.getMiles(User.getHeight());
        Difficulty = route.getRouteDifficulty();
        SurfaceDifficulty = route.getEvenVsUnevenSurface();
        Surface = route.getFlatVSHilly();
        RunType = route.getLoopVSOutBack();
        area = route.getStreetsVSTrail();
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
