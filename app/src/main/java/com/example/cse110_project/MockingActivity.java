package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import com.example.cse110_project.MainActivity;

import com.example.cse110_project.user_routes.User;


public class MockingActivity extends AppCompatActivity {
    public static final int STEPS_OFFSET_INCR = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocking);

        // creating back button
        Button back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(v -> finish());

        // linking 500+ button
        Button increment_button = findViewById(R.id.increment);
        increment_button.setOnClickListener(v -> incrementOffset());

    }

    public void incrementOffset() {
        int current_steps = User.getStepsOffset();
        current_steps = current_steps + STEPS_OFFSET_INCR;

        // Update step offset (separate from fitness steps)
        User.setStepsOffset(current_steps);
        System.out.println("Updated steps offset to " + current_steps);
    }
}
