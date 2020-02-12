package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cse110_project.trackers.CurrentTimeTracker;
import com.example.cse110_project.user_routes.User;

import java.time.LocalTime;


public class MockingActivity extends AppCompatActivity {
    public static final int STEPS_OFFSET_INCR = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocking);

        Button backButton = findViewById(R.id.mockingBackButton);
        backButton.setOnClickListener(v -> finish());

        Button incrementButton = findViewById(R.id.mockingStepsButton);
        incrementButton.setOnClickListener(v -> incrementStepsOffset());

        Button timeButton = findViewById(R.id.mockingTimeSubmitButton);
        timeButton.setOnClickListener(v -> setCurrentTime());
    }

    public void incrementStepsOffset() {
        int current_steps = User.getStepsOffset();
        current_steps = current_steps + STEPS_OFFSET_INCR;

        // Update step offset (separate from fitness steps)
        User.setStepsOffset(current_steps);
        System.out.println("Updated steps offset to " + current_steps);
    }

    public void setCurrentTime() {
        System.out.println("Time button clicked");
        EditText timeEditor = findViewById(R.id.mockingTimeInput);
        String timeInput = timeEditor.getText().toString();
        LocalTime time;

        try {
            time = LocalTime.parse(timeInput);
            CurrentTimeTracker.setTime(time);
            Toast.makeText(this, this.getResources().getString(R.string.mockingTimeSuccess),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, this.getResources().getString(R.string.mockingTimeError),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
