package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class TeamRoutesActivity extends AppCompatActivity {
    private final static String TAG = "TeamRoutesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_routes);

        // To other activities
        Button homeButton = findViewById(R.id.routesHomeButton);
        homeButton.setOnClickListener(v -> finish());
    }
}
