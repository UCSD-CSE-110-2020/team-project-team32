package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

import java.time.LocalDateTime;
import java.util.Date;

public class ScheduledDetails extends AppCompatActivity {

    User user; // this user
    private Route route; // route
    private LocalDateTime date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_details);

        user = WWRApplication.getUser();
        user.getTeam().getScheduledWalk(); // pull our scheduled walk

        route = user.getTeam().getScheduledWalk().getRoute();

        date = user.getTeam().getScheduledWalk().getDateTime();

        // To other activities
        Button homeButton = findViewById(R.id.scheduleToHomeButton);
        homeButton.setOnClickListener(v -> finish());


    }


}
