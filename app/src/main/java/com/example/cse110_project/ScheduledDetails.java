package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class ScheduledDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduled_details);

        // To other activities
        Button homeButton = findViewById(R.id.scheduleToHomeButton);
        homeButton.setOnClickListener(v -> finish());
    }
}
