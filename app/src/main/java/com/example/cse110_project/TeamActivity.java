package com.example.cse110_project;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TeamActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_screen_populated);

        Button backButton = findViewById(R.id.button2);
        backButton.setOnClickListener(v -> finish());

    }
}
