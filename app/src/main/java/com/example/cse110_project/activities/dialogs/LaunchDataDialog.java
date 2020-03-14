package com.example.cse110_project.activities.dialogs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.cse110_project.activities.MainActivity;

public class LaunchDataDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int secondsDelayed = 4000;
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(LaunchDataDialog.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, secondsDelayed);
    }
}
