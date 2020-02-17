package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.FitnessServiceFactory;
import com.example.cse110_project.fitness.GoogleFitAdapter;

// Used to launch MainActivity with appropriate fitness service setup
public class EntryActivity extends AppCompatActivity {
    private String fitnessServiceKey = "GOOGLE_FIT";
    private static final String TAG = "EntryActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        FitnessServiceFactory.put(fitnessServiceKey, GoogleFitAdapter::new);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.FITNESS_SERVICE_KEY, fitnessServiceKey);
        Log.d(TAG, "Added key: " + MainActivity.FITNESS_SERVICE_KEY + " to " +
                fitnessServiceKey + " : " +
                intent.getStringExtra(MainActivity.FITNESS_SERVICE_KEY));
        startActivity(intent);
    }
}
