package com.example.cse110_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cse110_project.R;
import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.user_routes.User;

import java.time.LocalTime;


public class MockingActivity extends AppCompatActivity {
    public static final int STEPS_OFFSET_INCR = 500;
    private static final String TAG = "MockingActivity";

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mocking);
        user = WWRApplication.getUser();

        Button backButton = findViewById(R.id.mockingBackButton);
        backButton.setOnClickListener(v -> finish());

        Button incrementButton = findViewById(R.id.mockingStepsButton);
        incrementButton.setOnClickListener(v -> incrementStepsOffset());

        Button timeButton = findViewById(R.id.mockingTimeSubmitButton);
        timeButton.setOnClickListener(v -> setCurrentTime());
    }

    public void incrementStepsOffset() {
        user.setStepsOffset(user.getStepsOffset() + STEPS_OFFSET_INCR);
        Log.d(TAG, "Updated steps offset to " + user.getStepsOffset());
    }

    public void setCurrentTime() {
        EditText timeEditor = findViewById(R.id.mockingTimeInput);
        String timeInput = timeEditor.getText().toString();
        LocalTime time;

        try {
            time = LocalTime.parse(timeInput);
            WWRApplication.setTime(time);
            Toast.makeText(this, this.getResources().getString(R.string.mockingTimeSuccess),
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, this.getResources().getString(R.string.mockingTimeError),
                    Toast.LENGTH_SHORT).show();
        }
    }
}
