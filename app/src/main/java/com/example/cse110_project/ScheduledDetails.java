package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.cse110_project.user_routes.Route;
import com.example.cse110_project.user_routes.User;

public class ScheduledDetails extends AppCompatActivity {

    User user; // this user
    private Route route; // route

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_details);

        user = WWRApplication.getUser();
        user.getTeam().getScheduledWalk(); // pull our scheduled walk

        // To other activities
        Button homeButton = findViewById(R.id.scheduleToHomeButton);
        homeButton.setOnClickListener(v -> finish());


    }


}
